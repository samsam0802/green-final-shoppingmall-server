package kr.kro.moonlightmoist.shopapi.review.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Table(name="reviews")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Check(constraints = "rating in(1,2,3,4,5)")
    private int rating;

    @Column(name="is_visible", nullable = false)
    @Builder.Default
    private boolean visible = true;

    @Column(name="is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

//    @Column(name="product_id")
//    private Long productId;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "product_id")
//    private Product product;

}
