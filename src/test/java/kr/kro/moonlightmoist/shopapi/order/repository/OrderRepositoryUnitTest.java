package kr.kro.moonlightmoist.shopapi.order.repository;

import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.repository.DeliveryPolicyRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class OrderRepositoryUnitTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeliveryPolicyRepository deliveryPolicyRepository;

    @Autowired
    OrderRepository orderRepository;

    private User user;
    private DeliveryPolicy deliveryPolicy;
    private Order order;

    @BeforeEach
    public void init() {
        user = userRepository.save(EntityFactory.createUser());
        deliveryPolicy = deliveryPolicyRepository.save(EntityFactory.createDeliveryPolicy());
        order = orderRepository.save(EntityFactory.createOrder(user));
    }


    @Test
    @DisplayName("주문 등록 테스트")
    public void registerOrder() {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getUser()).isNotNull();
        assertThat(order.getOrderNumber()).isEqualTo("주문번호");
        assertThat(order.getPaymentMethod()).isEqualTo("CARD");
        assertThat(order.getDeliveryFee()).isEqualTo(3000);
        assertThat(order.getExpectedDeliveryDate()).isEqualTo(LocalDate.of(2025,01,01));
        assertThat(order.getTotalProductAmount()).isEqualTo(30000);
        assertThat(order.getDiscountAmount()).isEqualTo(3000);
        assertThat(order.getUsedPoints()).isEqualTo(3000);
        assertThat(order.getFinalAmount()).isEqualTo(30000);
        assertThat(order.getReceiverName()).isEqualTo("이름");
        assertThat(order.getReceiverPhone()).isEqualTo("01012345678");
        assertThat(order.getPostalCode()).isEqualTo("123456");
        assertThat(order.getStreetAddress()).isEqualTo("도로명주소");
        assertThat(order.getDetailedAddress()).isEqualTo("상세주소");
        assertThat(order.getDeliveryRequest()).isEqualTo("배송요청사항");
        assertThat(order.getCreatedAt()).isNotNull();
        assertThat(order.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("주문 조회 테스트")
    public void checkOrder() {
        Order checked = orderRepository.findById(order.getId()).get();
        assertThat(checked.getId()).isNotNull();
        assertThat(checked.getUser()).isNotNull();
        assertThat(checked.getOrderNumber()).isEqualTo("주문번호");
        assertThat(checked.getPaymentMethod()).isEqualTo("CARD");
        assertThat(checked.getDeliveryFee()).isEqualTo(3000);
        assertThat(checked.getExpectedDeliveryDate()).isEqualTo(LocalDate.of(2025,01,01));
        assertThat(checked.getTotalProductAmount()).isEqualTo(30000);
        assertThat(checked.getDiscountAmount()).isEqualTo(3000);
        assertThat(checked.getUsedPoints()).isEqualTo(3000);
        assertThat(checked.getFinalAmount()).isEqualTo(30000);
        assertThat(checked.getReceiverName()).isEqualTo("이름");
        assertThat(checked.getReceiverPhone()).isEqualTo("01012345678");
        assertThat(checked.getPostalCode()).isEqualTo("123456");
        assertThat(checked.getStreetAddress()).isEqualTo("도로명주소");
        assertThat(checked.getDetailedAddress()).isEqualTo("상세주소");
        assertThat(checked.getDeliveryRequest()).isEqualTo("배송요청사항");
        assertThat(checked.getCreatedAt()).isNotNull();
        assertThat(checked.getUpdatedAt()).isNotNull();
    }

}