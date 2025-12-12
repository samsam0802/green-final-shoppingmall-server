package kr.kro.moonlightmoist.shopapi.order.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.order.dto.OrderProductResBySearch;
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
@Table(name = "order_products")
public class OrderProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_option_id",nullable = false)
    private ProductOption productOption;
    @Column(nullable = false)
    private int quantity;
    // 1개 가격
    @Column(nullable = false)
    private int purchasedPrice;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderProductStatus orderProductStatus;

    @Column(nullable = false, name = "is_deleted")
    private boolean deleted;

    public void updateStatus(OrderProductStatus orderProductStatus) {
        this.orderProductStatus=orderProductStatus;
    }

    public void deleteOrderProduct() {
        this.deleted=true;
    }

    public OrderProductResBySearch toDtoForOrderProductResBySearch() {
        OrderProductResBySearch orderProductRes = OrderProductResBySearch.builder()
                .id(this.getId())
                .productName(this.getProductOption().getProduct().getBasicInfo().getProductName())
                .productOptionName(this.getProductOption().getOptionName())
                .orderProductStatus(this.getOrderProductStatus())
                .quantity(this.getQuantity())
                .totalAmount(this.getPurchasedPrice()*this.getQuantity())
                .build();
        return orderProductRes;
    }

}
