package kr.kro.moonlightmoist.shopapi.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import kr.kro.moonlightmoist.shopapi.order.domain.QOrder;
import kr.kro.moonlightmoist.shopapi.order.domain.QOrderProduct;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderSearchCondition;
import kr.kro.moonlightmoist.shopapi.product.domain.QBasicInfo;
import kr.kro.moonlightmoist.shopapi.product.domain.QProduct;
import kr.kro.moonlightmoist.shopapi.product.domain.QProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OrderCustomRepositoryImpl implements OrderCustomRepository{

    private final JPAQueryFactory queryFactory;

    public OrderCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Order> search(OrderSearchCondition condition, Pageable pageable) {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProductOption productOption = QProductOption.productOption;
        QProduct product = QProduct.product;

        // 1. Contents Query (데이터 쿼리) 생성
        // JPQLQuery 객체로 쿼리 본체를 구성
        JPQLQuery<Order> query = queryFactory
                .selectFrom(order).distinct()
                .join(order.orderProducts,orderProduct)
                .join(orderProduct.productOption, productOption)
                .join(productOption.product, product)
                .where(
                        order.deleted.isFalse(),
                        userIdFilter(condition.getUserId()),
                        orderNumberFilter(condition.getOrderNumber()),
                        ordererNameFilter(condition.getOrdererName()),
                        productNameFilter(condition.getProductName(), product),
                        dateFilter(
                                condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null,
                                condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null
                        ),
                        orderStatusFilter(condition.getSelectedOrderStatus()),
                        //deliveryFilter(condition.getSelectedDelivery()),
                        paymentFilter(condition.getSelectedPayment())
                )
                .orderBy(order.createdAt.desc());

        // 2. 페이징 적용 (OFFSET, LIMIT)
        // Spring Data JPA의 Pageable 정보로 DB 쿼리에 페이징을 적용합니다.
        List<Order> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 3. Count Query (전체 개수 쿼리) 생성 및 실행
        // Count 쿼리는 JOIN으로 인한 중복을 막기 위해 countDistinct()를 사용하고,
        // SELECT 절을 제외한 WHERE 조건은 Contents Query와 동일하게 유지합니다.
        Long total = queryFactory
                .select(order.countDistinct()) // DISTINCT를 사용하여 정확한 주문 건수 카운트
                .from(order)
                .join(order.orderProducts, orderProduct)
                .join(orderProduct.productOption, productOption)
                .join(productOption.product, product)
                .where(
                        order.deleted.isFalse(),
                        userIdFilter(condition.getUserId()),
                        orderNumberFilter(condition.getOrderNumber()),
                        ordererNameFilter(condition.getOrdererName()),
                        productNameFilter(condition.getProductName(), product),
                        dateFilter(
                                condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null,
                                condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null
                        ),
                        orderStatusFilter(condition.getSelectedOrderStatus()),
                        paymentFilter(condition.getSelectedPayment())
                )
                .fetchOne();

        // 4. PageImpl 객체로 반환
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression userIdFilter(Long userId) {
        if(userId == null) return null;

        QOrder order = QOrder.order;
        return order.user.id.eq(userId);
    }


    private BooleanExpression orderNumberFilter(String orderNumber) {
        if(orderNumber == null || orderNumber.trim().isEmpty()) {
            return null;
        }

        QOrder order = QOrder.order;
        return order.orderNumber.contains(orderNumber);
    }

    private BooleanExpression ordererNameFilter(String ordererName) {
        if(ordererName == null || ordererName.trim().isEmpty()) return null;

        QOrder order = QOrder.order;
        return order.user.name.contains(ordererName);
    }

    private BooleanExpression productNameFilter(String productName, QProduct product) {
        if(productName == null || productName.trim().isEmpty()) return null;

        String[] keywords = productName.trim().split("\\s+");
        BooleanExpression expression = null;

        for(String keyword : keywords) {
            if(!keyword.trim().isEmpty()){
                BooleanExpression current = product.basicInfo.productName.isNotNull()
                        .and(product.basicInfo.productName.contains(keyword));
                expression = expression == null ? current : expression.or(current);
            }
        }

        return expression;
    }

    private BooleanExpression dateFilter(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(startDateTime == null && endDateTime == null) {
            return null;
        }

        QOrder order = QOrder.order;

        if (startDateTime != null && endDateTime != null) {
            return order.createdAt.between(startDateTime,endDateTime);
        } else if (startDateTime != null) {
            return order.createdAt.goe(startDateTime);
        } else if (endDateTime != null) {
            return order.createdAt.loe(endDateTime);
        }

        return null;
    }

    private BooleanExpression orderStatusFilter(List<OrderProductStatus> selectedOrderStatus) {
        if(selectedOrderStatus == null || selectedOrderStatus.isEmpty()) return null;

        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        return orderProduct.orderProductStatus.in(selectedOrderStatus);
    }

//    private BooleanExpression deliveryFilter(List<String> selectedDelivery) {
//        if(selectedDelivery == null || selectedDelivery.isEmpty()) return null;
//
//        QO
//    }

    private BooleanExpression paymentFilter(List<String> selectedPayment) {
        if(selectedPayment == null || selectedPayment.isEmpty()) return null;

        QOrder order = QOrder.order;

        return order.paymentMethod.in(selectedPayment);
    }

}
