package kr.kro.moonlightmoist.shopapi.order.service;

import com.siot.IamportRestClient.response.Payment;
import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.domain.DiscountType;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderCoupon;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import kr.kro.moonlightmoist.shopapi.order.dto.*;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderCouponRepository;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.pointHistory.service.PointHistoryService;
import kr.kro.moonlightmoist.shopapi.product.domain.ImageType;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductMainImage;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import kr.kro.moonlightmoist.shopapi.usercoupon.dto.UserCouponRes;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UserCouponRepository userCouponRepository;
    private final OrderCouponRepository orderCouponRepository;

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
            ProductOption productOption = productOptionRepository.findById(item.getProductOptionId()).get();
            totalProductAmount += productOption.getSellingPrice() * item.getQuantity();
        }
        return totalProductAmount;
    }

    public int calcDeliveryFee(int totalProductAmount, List<OrderProductRequestDTO> orderProducts) {
        ProductOption productOption = productOptionRepository.findById(orderProducts.get(0).getProductOptionId()).get();
        int basicDeliveryFee = productOption.getProduct().getDeliveryPolicy().getBasicDeliveryFee();
        int freeConditionAmount = productOption.getProduct().getDeliveryPolicy().getFreeConditionAmount();
        if(totalProductAmount >= freeConditionAmount) return 0;
        return basicDeliveryFee;
    }

    public OrderResponseDTO toDto(Order order) {
        OrderCoupon orderCoupon = order.getOrderCoupon();
        OrderCouponResponseDTO orderCouponResponseDTO = null;
        if(orderCoupon != null) {
            orderCouponResponseDTO = orderCoupon.toDto();
        }

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .deliveryFee(order.getDeliveryFee())
                .deliveryRequest(order.getDeliveryRequest())
                .detailedAddress(order.getDetailedAddress())
                .discountAmount(order.getDiscountAmount())
                .usedPoints(order.getUsedPoints())
                .finalAmount(order.getFinalAmount())
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .streetAddress(order.getStreetAddress())
                .paymentMethod(order.getPaymentMethod())
                .totalProductAmount(order.getTotalProductAmount())
                .postalCode(order.getPostalCode())
                .orderDate(order.getCreatedAt().toLocalDate())
                .orderCoupon(orderCouponResponseDTO)
                .build();


        for(OrderProduct op : order.getOrderProducts()){
            OrderProductResponseDTO orderProductResponseDTO = OrderProductResponseDTO.builder()
                    .id(op.getId())
                    .productId(op.getProductOption().getProduct().getId())
                    .brandName(op.getProductOption().getProduct().getBrand().getName())
                    .productName(op.getProductOption().getProduct().getBasicInfo().getProductName())
                    .productOptionName(op.getProductOption().getOptionName())
                    .purchasedPrice(op.getPurchasedPrice())
                    .quantity(op.getQuantity())
                    .imageUrl(op.getProductOption().getProduct().getMainImages().stream().filter(image->image.getImageType() == ImageType.THUMBNAIL).map(ProductMainImage::getImageUrl).findFirst().orElse(null))
                    .orderProductStatus(op.getOrderProductStatus())
                    .build();
            orderResponseDTO.getOrderProducts().add(orderProductResponseDTO);
        }
        return orderResponseDTO;
    }

    @Override
    public Long createOrder(OrderRequestDTO dto, Long userId) {
        log.info("OrderService createOrder dto=>{}, userId=>{}",dto,userId);
        User user = userRepository.findById(userId).orElseThrow();

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
            ProductOption productOption = productOptionRepository.findById(item.getProductOptionId()).get();
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
            OrderCoupon orderCoupon = orderCouponRepository.findById(orderCouponId).get();

            // 주문 엔티티에 주문에 사용된 쿠폰 저장
            order.applyOrderCoupon(orderCoupon);
        }

        return order.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        OrderResponseDTO orderResponseDTO = toDto(order);
        return orderResponseDTO;
    }

    @Override
    public List<OrderResponseDTO> getOrderList(Long userId) {
        List<Order> orderByUserId = orderRepository.findOrderByUserId(userId);
        log.info("orderByUserId : {}",orderByUserId);
        List<OrderResponseDTO> ListOfOrderResponseDTO = new ArrayList<>();
        for(Order o : orderByUserId){
            OrderResponseDTO orderResponseDTO = toDto(o);
            ListOfOrderResponseDTO.add(orderResponseDTO);
        }

        return ListOfOrderResponseDTO;
    }

    @Override
    public BigDecimal getExpectedAmount(String merchantUid) {
        return BigDecimal.valueOf(orderRepository.findByMerchantUid(merchantUid).get().getFinalAmount());
    }

    @Override
    public void completeOrder(String merchantUid) {
        // 1. 주문 조회
        Order order = orderRepository.findByMerchantUid(merchantUid).get();

        // 2. 주문 상품들의 상태를 PAID로 변경
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.updateStatus(OrderProductStatus.PAID);
        }

        log.info("주문 결제 완료 처리. 주문번호: {}", merchantUid);

    }

    @Override
    public void deleteOneOrder(Long orderId) {
        log.info("deleteOneOrder 메서드 실행 orderId:{}",orderId);
        Order order = orderRepository.findById(orderId).get();
        OrderCoupon orderCoupon = order.getOrderCoupon();
        if(orderCoupon != null ){
            orderCoupon.getUserCoupon().recoverCoupon();
            orderCouponService.deleteOrderCoupon(orderCoupon.getId());
        }
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<OrderResBySearch> searchOrdersByCondition(OrderSearchCondition condition) {
        List<Order> orderList = orderRepository.search(condition);
        List<OrderResBySearch> orderResBySearches = orderList.stream().map(o->o.toDtoForOrderResBySearch()).toList();
        return orderResBySearches;
    }

}
