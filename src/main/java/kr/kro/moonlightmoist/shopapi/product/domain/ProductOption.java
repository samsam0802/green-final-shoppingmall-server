package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "product_options")
public class ProductOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(unique = true, nullable = false)
    private String optionName;

    @Column(nullable = false)
    private int purchasePrice;

    @Column(nullable = false)
    private int sellingPrice;

    @Column(nullable = false)
    private int currentStock;

    @Column(nullable = false)
    private int initialStock;

    @Column(nullable = false)
    private int safetyStock;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private int displayOrder;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    public void changeOptionName(String name) {
        this.optionName = name;
    }

    public void deleteProductOption() {
        this.deleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductOption that = (ProductOption) o;

        if (this.getId() == null || that.getId() == null) {
            return false; // id가 없다면(새로운 엔티티) 논리적으로 다르다고 판단
        }

        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return Objects.hash(0);
        }
        // id가 있다면 id를 기반으로 해시 코드 생성
        return Objects.hash(this.getId());
    }

}
