package kr.kro.moonlightmoist.shopapi.cart.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    // OneToMany는 조회용으로 쓴다
    // ManyToOne이 주인(?)이다.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CartProduct> cartProducts = new ArrayList<>();

    public void addCartProduct(CartProduct cartProduct) {
        cartProducts.add(cartProduct);
        //cartProduct는 주소를 참조하는 객체이기 때문에 cart를 나중에 지정해줘도 리스트에 반영된다.
        cartProduct.setCart(this);
    }
    public void removeCartProduct(CartProduct cartProduct){
        cartProducts.remove(cartProduct);
        cartProduct.setCart(null);
    }
}
