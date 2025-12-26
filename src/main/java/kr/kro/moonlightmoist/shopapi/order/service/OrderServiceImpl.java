package kr.kro.moonlightmoist.shopapi.order.service;

import com.siot.IamportRestClient.response.Payment;
import jakarta.persistence.EntityNotFoundException;
import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.domain.DiscountType;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderCoupon;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import kr.kro.moonlightmoist.shopapi.order.dto.*;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderCouponRepository;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderProductRepository;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.pointHistory.service.PointHistoryService;
import kr.kro.moonlightmoist.shopapi.product.domain.ImageType;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductMainImage;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final OrderProductRepository orderProductRepository;

    private final OrderCouponService orderCouponService;



    public String createOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        //랜덤 6자리 생성(UUID 사용)
        String random = UUID.randomUUID()
                .toString()
                .substring(0,6)
                .toUpperCase();
        return date + "-" + random;
    }

    public int calcTotalProductAmount(List<OrderProductRequestDTO> orderProducts) {
        int totalProductAmount = 0;
        for(OrderProductRequestDTO item : orderProducts){
            ProductOption productOption = productOptionRepository.findById(item.getProductOptionId())
                    .orElseThrow(()-> new RuntimeException("상품 옵션을 찾을 수 없습니다."));
            totalProductAmount += productOption.getSellingPrice() * item.getQuantity();
        }
        return totalProductAmount;
    }

    public int calcDeliveryFee(int totalProductAmount, List<OrderProductRequestDTO> orderProducts) {
        ProductOption productOption = productOptionRepository.findById(orderProducts.get(0).getProductOptionId())
                .orElseThrow(()-> new RuntimeException("상품 옵션을 찾을 수 없습니다."));
        int basicDeliveryFee = productOption.getProduct().getDeliveryPolicy().getBasicDeliveryFee();
        int freeConditionAmount = productOption.getProduct().getDeliveryPolicy().getFreeConditionAmount();
        if(totalProductAmount >= freeConditionAmount) return 0;
        return basicDeliveryFee;
    }

    @Override
    public Long createOrder(OrderRequestDTO dto, Long userId) {
        log.info("OrderService createOrder dto=>{}, userId=>{}",dto,userId);
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 1) 주문 번호 생성
        String orderNumber = createOrderNumber();
        // 2) 예상 배송일 생성
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(2);
        // 3) 전체 상품 가격 계산
        int totalProductAmount = calcTotalProductAmount(dto.getOrderProducts());
        // 4) 배송비 계산
        int deliveryFee = calcDeliveryFee(totalProductAmount, dto.getOrderProducts());
        // 5) 쿠폰 할인 가격 계산
        int discountAmount = orderCouponService.calcAndUseCoupon(totalProductAmount, dto.getUserCouponId());
        // 6) 포인트 계산(사용 포인트)
        int usedPoints = dto.getUsedPoints();
        // 7) 최종 결제 금액 계산
        int finalAmount = totalProductAmount + deliveryFee - discountAmount - usedPoints;

        // 주문 생성
        Order order = Order.builder()
                .user(user)
                .orderNumber(orderNumber)
                .paymentMethod(dto.getPaymentMethod())
                .deliveryFee(deliveryFee)
                .expectedDeliveryDate(expectedDeliveryDate)
                .totalProductAmount(totalProductAmount)
                .discountAmount(discountAmount)
                .usedPoints(usedPoints)
                .earnedPoints(dto.getEarnedPoints())
                .finalAmount(finalAmount)
                .receiverName(dto.getReceiverName())
                .receiverPhone(dto.getReceiverPhone())
                .postalCode(dto.getPostalCode())
                .streetAddress(dto.getStreetAddress())
                .detailedAddress(dto.getDetailedAddress())
                .deliveryRequest(dto.getDeliveryRequest())
                .deleted(false)
                .build();

        for(OrderProductRequestDTO item : dto.getOrderProducts()) {
            ProductOption productOption = productOptionRepository.findById(item.getProductOptionId())
                    .orElseThrow(()-> new RuntimeException("상품 옵션을 찾을 수 없습니다."));

            // 상품 옵션 재고 차감
            productOption.decreaseStock(item.getQuantity());

            // 주문 상품 생성
            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .productOption(productOption)
                    .quantity(item.getQuantity())
                    .purchasedPrice(productOption.getSellingPrice())
                    .orderProductStatus(OrderProductStatus.PENDING_PAYMENT)
                    .build();

            // 주문 상품 리스트에 주문 상품 추가
            order.getOrderProducts().add(orderProduct);
        }

        orderRepository.save(order);

        // 주문에 사용된 쿠폰 정보를 db에 저장
        if(dto.getUserCouponId() != null && discountAmount > 0) {
            Long orderCouponId = orderCouponService.saveCoupon(order.getId(), dto.getUserCouponId(), discountAmount);
            OrderCoupon orderCoupon = orderCouponRepository.findById(orderCouponId)
                    .orElseThrow(()-> new RuntimeException("주문에 사용된 쿠폰을 찾을 수 없습니다."));

            // 주문 엔티티에 주문에 사용된 쿠폰 저장
            order.applyOrderCoupon(orderCoupon);
        }

        return order.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("주문을 찾을 수 없습니다."));
        OrderResponseDTO orderResponseDTO = order.toDto();
        return orderResponseDTO;
    }

//    @Override
//    public PageResponseDTO<OrderResponseDTO> getOrderList(Long userId, String sort, PageRequestDTO pageRequestDTO) {
//        // 1. Pageable 객체 생성 및 정렬 적용 (요청된 페이징 및 정렬 정보를 DB 쿼리에 전달)
//        int page = pageRequestDTO.getPage() - 1; // 페이지 번호 (0부터 시작)
//        int size = pageRequestDTO.getSize() == null ? 10 : pageRequestDTO.getSize();
//
//        // Sort 객체 생성 및 요청된 sort 값에 따라 업데이트
//        Sort sortBy = Sort.by("createdAt").descending(); // 기본 정렬 : 최신순
//
//        if ("latest".equals(sort)) {
//            sortBy = Sort.by("createdAt").descending();
//        }
//        else if("earliest".equals(sort)) {
//            sortBy = Sort.by("createdAt").ascending();
//        }
//
//        // Pageable 객체 생성
//        Pageable pageable = PageRequest.of(page, size, sortBy);
//
//        // 2. DB에서 요청된 페이지의 데이터만 Page<Order>로 조회
//        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
//
//        // 3. Page<Order> 데이터를 List<OrderResponseDTO>로 매핑
//        List<OrderResponseDTO> ListOfOrderResponseDTO = orderPage.getContent().stream()
//                .map(o->o.toDto()) // toDto 메서드를 사용하여 Order -> OrderResponseDTO 변환
//                .toList();
//
//        // 4. PageResponseDTO 반환
//        return PageResponseDTO.<OrderResponseDTO>withAll()
//                .dtoList(ListOfOrderResponseDTO) // 현재 페이지의 DTO 리스트
//                .pageRequestDTO(pageRequestDTO)
//                .totalDataCount(orderPage.getTotalElements()) // DB에서 계산된 전체 항목 수
//                .build();
//    }

//    @Override
//    public BigDecimal getExpectedAmount(String merchantUid) {
//        return BigDecimal.valueOf(orderRepository.findByMerchantUid(merchantUid)
//                .orElseThrow(()-> new RuntimeException("해당 주문 번호로 주문을 찾을 수 없습니다."))
//                .getFinalAmount());
//    }

    @Override
    public void completeOrder(String merchantUid, String impUid) {
        // 1. 주문 조회
        Order order = orderRepository.findByMerchantUid(merchantUid)
                .orElseThrow(()-> new RuntimeException("해당 주문 번호로 주문을 찾을 수 없습니다."));
        // 2. 포트원 시스템에서 생성한 결제 고유 번호 저장
        order.applyImpUid(impUid);
        // 3. 주문 상품들의 상태를 PAID로 변경
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.updateStatus(OrderProductStatus.PAID);
        }

        // 4. 주문의 결제 날짜 저장
        order.decidePaidAt(LocalDateTime.now());

        log.info("주문 결제 완료 처리. 주문번호: {}", merchantUid);

    }

    @Override
    public void deleteOneOrder(Long orderId) {
        log.info("deleteOneOrder 메서드 실행 orderId:{}",orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("주문을 찾을 수 없습니다."));

        // 쿠폰 복구
        OrderCoupon orderCoupon = order.getOrderCoupon();
        if(orderCoupon != null ){
            orderCoupon.getUserCoupon().recoverCoupon();
            orderCouponService.deleteOrderCoupon(orderCoupon.getId());
        }
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            // 재고수량 다시 증가
            ProductOption productOption = orderProduct.getProductOption();
            productOption.increaseStock(orderProduct.getQuantity());

            orderProduct.deleteOrderProduct();
        }
        order.deleteOrder();
    }

    @Override
    public PageResponseDTO<OrderResBySearch> searchOrdersByCondition(OrderSearchCondition condition, String sort, PageRequestDTO pageRequestDTO) {
        log.info("sort:{}",sort);
        // 1. Pageable 객체 생성
        int page = pageRequestDTO.getPage() - 1;
        int size = pageRequestDTO.getSize() == null ? 10 : pageRequestDTO.getSize();
        // sort 설정 (Pageable에 맞게 컬럼명 정확히 지정)
//        Sort sortBy = "latest".equals(sort) ? Sort.by("createdAt").descending() : Sort.by("createdAt").ascending();
        Sort sortBy = Sort.by("createdAt").descending();
        if("latest".equals(sort)) {
            sortBy = Sort.by("createdAt").descending();
        } else if("earliest".equals(sort)) {
            sortBy = Sort.by("createdAt").ascending();
        }

        log.info("sortBy:{}",sortBy);

        //Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size, sortBy);

        // 2. Page<Order> 객체로 받음
        Page<Order> orderPage = orderRepository.search(condition, pageable);

        // 3. List<OrderResBySearch> 생성
        List<OrderResBySearch> ListOforderResBySearch = orderPage.getContent().stream().map(o->o.toDtoForOrderResBySearch()).toList();

        return PageResponseDTO.<OrderResBySearch>withAll()
                .dtoList(ListOforderResBySearch)
                .pageRequestDTO(pageRequestDTO)
                .totalDataCount(orderPage.getTotalElements())
                .build();
    }

    @Override
    public void comfirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        for(OrderProduct orderProduct : order.getOrderProducts()){
            // 판매량 증가
            Product product = orderProduct.getProductOption().getProduct();
            product.getSaleInfo().increaseSalesCount(orderProduct.getQuantity());
            // 구매 확정 처리
            orderProduct.updateStatus(OrderProductStatus.CONFIRMED);
        }
    }

    @Override
    public void changeOrderProductStatus(Long orderId, OrderProductStatus status, String reason) {
        Order order = orderRepository.findById(orderId).get();
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.updateStatus(status);
            if(status == OrderProductStatus.RETURN_REQUESTED) {
                orderProduct.setReturnReason(reason);
            }
        }
    }

    @Override
    public void checkRefundable(String merchantUid, Long currentUserId) {
        // 1. 주문 존재 여부 확인
        Order order = orderRepository.findByMerchantUid(merchantUid).orElseThrow(()->new IllegalArgumentException("존재하지 않는 주문입니다."));

        // 2. 권한 확인 (내 주문이 맞는지)
        if (!order.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("본인의 주문만 환불 신청이 가능합니다.");
        }

        // 3. 주문 상태 확인
        // 결제 완료(PAID) 또는 환불 신청(REFUND_REQUESTED) 상태일 때만 환불 가능. 이미 환불(REFUNDED)되었거나 배송 준비중(PREPARING) 또는 배송중(SHIPPING) 또는 배송완료(DELIVERED)이면 불가.
        OrderProduct orderProduct = order.getOrderProducts().get(0);
            if(!(orderProduct.getOrderProductStatus() == OrderProductStatus.PAID) && !(orderProduct.getOrderProductStatus() == OrderProductStatus.RETURN_REQUESTED)) {
                if(orderProduct.getOrderProductStatus() == OrderProductStatus.RETURNED) {
                    throw new IllegalStateException("이미 환불 처리된 주문입니다.");
                } else if (orderProduct.getOrderProductStatus() == OrderProductStatus.PREPARING || orderProduct.getOrderProductStatus() == OrderProductStatus.SHIPPING || orderProduct.getOrderProductStatus() == OrderProductStatus.DELIVERED) {
                    throw new IllegalStateException("배송이 시작된 상품은 고객센터로 문의해주세요.");
                } else {
                    throw new IllegalStateException("환불이 불가능한 주문 상태입니다. (상태: " + orderProduct.getOrderProductStatus() + ")");
                }
            }

            // 4. 환불 가능 기간 확인
            if(order.getPaidAt().isBefore(LocalDateTime.now().minusDays(7))) {
                throw new IllegalStateException("환불 가능 기간(7일)이 지났습니다.");
            }
    }

    @Override
    public Map<String, Long> getOrderStatusSummary(Long userId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        List<Object[]> results = orderProductRepository.countStatusByUserIdAndPeriod(userId, start, end);

        Map<String, Long> orderSummary = new HashMap<>();

        for(Object[] result : results) {
            orderSummary.put(result[0].toString(),(Long) result[1]);
        }

        return orderSummary;
    }

//    @Override
//    @Transactional
//    public void completeRefund(String merchantUid) {
//        Order order = orderRepository.findByMerchantUid(merchantUid).orElseThrow(()->new EntityNotFoundException("주문을 찾을 수 없습니다."));
//
//       // 포인트 복구
//
//
//        // 마지막 : 소프트 삭제(주문과 주문 상품)
//        order.deleteOrder();
//    }

}
