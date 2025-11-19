package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"productOptions", "mainImages", "detailImages"})
@Getter
@Builder
@Setter
@Table(name = "products")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(unique = true, nullable = false)
    private String productName;

    @Column(unique = true, nullable = false)
    private String productCode;

    private String searchKeywords;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExposureStatus exposureStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    private String description;

    @Column(name = "is_cancelable", nullable = false)
    @Builder.Default
    private boolean cancelable = true;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private List<ProductMainImage> mainImages = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private List<ProductDetailImage> detailImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ProductOption> productOptions = new ArrayList<>();


    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void deleteProduct() {
        this.deleted = true;
        this.productOptions.forEach(ProductOption::deleteProductOption);
    }

    public void addProductOption(ProductOption option) {
        this.productOptions.add(option);
        option.setProduct(this);
    }

    public void removeProductOption(ProductOption option) {
        boolean removed = this.productOptions.remove(option);
        if (removed) {
            option.setProduct(null);
        }
    }

}
