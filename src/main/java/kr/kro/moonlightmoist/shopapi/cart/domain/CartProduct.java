package kr.kro.moonlightmoist.shopapi.cart.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_products")
@AttributeOverride(name = "createdAt", column = @Column(name = "added_at",updatable = false, nullable = false))
public class CartProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_option_id",nullable = false)
    private ProductOption productOption;
    @Column(nullable = false)
    private int quantity;

    public void changeQty(int quantity) {
        this.quantity = quantity;
    }
}

