package kr.kro.moonlightmoist.shopapi.order.service;

import kr.kro.moonlightmoist.shopapi.coupon.domain.Coupon;
import kr.kro.moonlightmoist.shopapi.coupon.domain.DiscountType;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderCoupon;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderCouponRepository;
import kr.kro.moonlightmoist.shopapi.order.repository.OrderRepository;
import kr.kro.moonlightmoist.shopapi.usercoupon.domain.UserCoupon;
import kr.kro.moonlightmoist.shopapi.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderCouponServiceImpl implements OrderCouponService{
    private final OrderRepository orderRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final UserCouponRepository userCouponRepository;

    @Override
    public int calcAndUseCoupon(int totalProductAmount, Long userCouponId) {
        log.info("OrderCouponServiceImpl -> userCouponId:{}",userCouponId);
        if(userCouponId != null) {
            UserCoupon userCoupon = userCouponRepository.findById(userCouponId).get();
            Coupon coupon = userCoupon.getCoupon();
            if (!coupon.getLimitMinOrderAmount() || (coupon.getLimitMinOrderAmount() && (totalProductAmount >= coupon.getMinOrderAmount()))) {
                if (coupon.getDiscountType() == DiscountType.FIXED) {
                    userCoupon.useCoupon();
                    return coupon.getFixedDiscountAmount();
                } else {// 할인 타입이 PERCENTAGE일 경우
                    int discountAmount = totalProductAmount * coupon.getDiscountPercentage() / 100;
                    if (discountAmount > coupon.getMaxDiscountAmount()) {
                        userCoupon.useCoupon();
                        return coupon.getMaxDiscountAmount();
                    }
                    userCoupon.useCoupon();
                    return discountAmount;
                }
            } else { // 최소 주문 금액이 안 될 경우
                return 0;
            }
        } else { // userCouponId가 null일 경우(쿠폰을 선택하지 않았을 경우)
            return 0;
        }
    }

    @Override
    public Long saveCoupon(Long orderId, Long userCouponId, int discountAmount) {
        Order order = orderRepository.findById(orderId).get();
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId).get();
        OrderCoupon orderCoupon = OrderCoupon.builder()
                .order(order)
                .userCoupon(userCoupon)
                .discountAmount(discountAmount)
                .couponCode(userCoupon.getCoupon().getCouponCode())
                .discountType(userCoupon.getCoupon().getDiscountType())
                .build();
        OrderCoupon savedOrderCoupon = orderCouponRepository.save(orderCoupon);
        return savedOrderCoupon.getId();
    }

    @Override
    public void deleteOrderCoupon(Long orderCouponId) {
        OrderCoupon orderCoupon = orderCouponRepository.findById(orderCouponId).get();
        orderCoupon.deleteOrderCoupon();
    }
}
