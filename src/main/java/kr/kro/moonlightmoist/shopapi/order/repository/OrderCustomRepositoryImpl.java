package kr.kro.moonlightmoist.shopapi.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OrderCustomRepositoryImpl implements OrderCustomRepository{

    private final JPAQueryFactory queryFactory;

    public OrderCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> search(OrderSearchCondition condition) {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProductOption productOption = QProductOption.productOption;
        QProduct product = QProduct.product;

        List<Order> orderList = queryFactory
                .selectFrom(order)
                .join(order.orderProducts,orderProduct)
                .join(orderProduct.productOption, productOption)
                .join(productOption.product, product)
                .where(
                        order.deleted.isFalse(),
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
                .orderBy(order.createdAt.desc())
                .fetch();

        System.out.println("result= " + orderList);
        System.out.println("result.size() = " + orderList.size());

        return orderList;
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
