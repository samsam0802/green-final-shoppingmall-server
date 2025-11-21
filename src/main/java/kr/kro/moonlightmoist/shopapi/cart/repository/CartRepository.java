package kr.kro.moonlightmoist.shopapi.cart.repository;

import kr.kro.moonlightmoist.shopapi.cart.domain.Cart;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByOwner(User owner);
}
