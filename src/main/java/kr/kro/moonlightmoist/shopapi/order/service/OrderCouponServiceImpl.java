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
//        log.info("OrderCouponServiceImpl -> userCouponId:{}",userCouponId);
        if(userCouponId == null) return 0;

        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(()->new RuntimeException("쿠폰을 찾을 수 없습니다."));
        Coupon coupon = userCoupon.getCoupon();

        // 1. 최소 주문 금액 조건 체크
        if(Boolean.TRUE.equals(coupon.getLimitMinOrderAmount()) && totalProductAmount < coupon.getMinOrderAmount()) {
            return 0;
        }

        // 2. 할인 금액 계산
        int discountAmount = 0;
        if(coupon.getDiscountType() == DiscountType.FIXED) {
            discountAmount = coupon.getFixedDiscountAmount();
        } else {
            discountAmount = (int) ((long) totalProductAmount * coupon.getDiscountPercentage() / 100);

            //최대 할인 금액 제한 적용
            if(Boolean.TRUE.equals(coupon.getLimitMaxDiscountAmount()) &&
                    discountAmount > coupon.getMaxDiscountAmount()) {
                discountAmount = coupon.getMaxDiscountAmount();
            }
        }

        // 3. 쿠폰 사용 처리
        userCoupon.useCoupon();

        return discountAmount;
    }

    @Override
    public Long saveCoupon(Long orderId, Long userCouponId, int discountAmount) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("주문을 찾을 수 없습니다."));
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId).orElseThrow(()->new RuntimeException("쿠폰을 찾을 수 없습니다."));
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
        OrderCoupon orderCoupon = orderCouponRepository.findById(orderCouponId).orElseThrow(()->new RuntimeException("주문에 사용된 쿠폰을 찾을 수 없습니다."));
        orderCoupon.deleteOrderCoupon();
    }
}
