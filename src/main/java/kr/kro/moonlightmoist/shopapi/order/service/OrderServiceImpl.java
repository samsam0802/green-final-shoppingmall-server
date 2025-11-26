package kr.kro.moonlightmoist.shopapi.order.service;

import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderRequestDTO;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderResponseDTO;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductOptionRepository productOptionRepository;


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

    public int calcCouponDiscountAmount(int totalProductAmount, Long couponId) {
        if(couponId == null){
            return 0;
        }
        else return 3000;
    }

    public int calcDeliveryFee(int totalProductAmount) {
        int basicFee = 3000;
        int freeConditionAmount = 50000;
        if(totalProductAmount >= 50000) return 0;
        return basicFee;
    }

    public OrderResponseDTO toDto(Order order) {
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
                .build();
        for(OrderProduct op : order.getOrderProducts()){
            OrderProductResponseDTO orderProductResponseDTO = OrderProductResponseDTO.builder()
                    .id(op.getId())
                    .brandName(op.getProductOption().getProduct().getBrand().getName())
                    .productName(op.getProductOption().getProduct().getBasicInfo().getProductName())
                    .productOptionName(op.getProductOption().getOptionName())
                    .purchasedPrice(op.getPurchasedPrice())
                    .quantity(op.getQuantity())
//                    .imageUrl(op.getProductOption().getProduct().getMainImages().stream().filter(image->image.getImageType()==="THUMBNAIL"))
                    .build();
            orderResponseDTO.getOrderProducts().add(orderProductResponseDTO);
        }
        return orderResponseDTO;
    }

    @Override
    public Long createOrder(OrderRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        // 1) 주문 번호 생성
        String orderNumber = createOrderNumber();
        // 2) 예상 배송일 생성
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(2);
        // 3) 전체 상품 가격 계산
        int totalProductAmount = calcTotalProductAmount(dto.getOrderProducts());
        // 4) 배송비 계산
        int deliveryFee = calcDeliveryFee(totalProductAmount);
        // 5) 쿠폰 할인 가격 계산
        int discountAmount = calcCouponDiscountAmount(totalProductAmount,dto.getCouponId());
        // 6) 최종 결제 금액 계산
        int finalAmount = totalProductAmount- discountAmount - dto.getUsedPoints();

        // 주문 생성
        Order order = Order.builder()
                .user(user)
                .orderNumber(orderNumber)
                .paymentMethod(dto.getPaymentMethod())
                .deliveryFee(deliveryFee)
                .expectedDeliveryDate(expectedDeliveryDate)
                .totalProductAmount(totalProductAmount)
                .discountAmount(discountAmount)
                .usedPoints(dto.getUsedPoints())
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
                    .orderProductStatus(OrderProductStatus.PAID)
                    .build();

            // 주문 상품 리스트에 주문 상품 추가
            order.getOrderProducts().add(orderProduct);
        }

        orderRepository.save(order);
        return order.getId();
    }

    @Override
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

}
