package kr.kro.moonlightmoist.shopapi.cart.repository;

import kr.kro.moonlightmoist.shopapi.cart.domain.Cart;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;

import kr.kro.moonlightmoist.shopapi.user.repository.UserGradeRepository;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class CartRepositoryUnitTest {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserGradeRepository userGradeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("장바구니 생성 테스트")
    public void registerCart() {
        UserGrade userGrade = EntityFactory.createUserGrade();
        UserGrade savedUserGrade = userGradeRepository.save(userGrade);

        User user = EntityFactory.createUser(savedUserGrade);
        User savedUser = userRepository.save(user);

        Cart cart = Cart.builder()
                .user(savedUser)
                .build();
        Cart savedCart = cartRepository.save(cart);

        assertThat(savedCart.getId()).isNotNull();
        assertThat(savedCart.getUser()).isNotNull();
        assertThat(savedCart.getCreatedAt()).isNotNull();
        assertThat(savedCart.getUpdatedAt()).isNotNull();
    }

}