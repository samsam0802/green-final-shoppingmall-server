package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForDetail;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import lombok.*;

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

    @Embedded
    private BasicInfo basicInfo;

    @Embedded
    private SaleInfo saleInfo;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "delivery_policy_id")
    private DeliveryPolicy deliveryPolicy;

    @ElementCollection
    @CollectionTable(
            name = "product_main_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private List<ProductMainImage> mainImages = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "product_detail_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private List<ProductDetailImage> detailImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ProductOption> productOptions = new ArrayList<>();

    public void addMainImage(ProductMainImage image) {
        image.setDisplayOrder(this.mainImages.size());
        mainImages.add(image);
    }

    public void addDetailImage(ProductDetailImage image) {
        image.setDisplayOrder(detailImages.size());
        detailImages.add(image);
    }

    public void changeProductBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
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

    public ProductResForList toDTOForList() {
        return ProductResForList.builder()
                .id(this.id)
                .basicInfo(this.basicInfo.toDTO())
                .saleInfo(this.saleInfo.toDTO())
                .brand(this.brand.toDTO())
                .category(this.category.toCategoryResForList())
                .deliveryPolicy(this.deliveryPolicy.toDTO())
                .mainImages(this.mainImages.stream().map(image -> image.toDTO()).toList())
                .options(this.getProductOptions().stream().map(option -> option.toDTO()).toList())
                .build();
    }

    public ProductResForDetail toDTOForDetail() {
        return ProductResForDetail.builder()
                .id(this.id)
                .basicInfo(this.basicInfo.toDTO())
                .saleInfo(this.saleInfo.toDTO())
                .brand(this.brand.toDTO())
                .category(this.category.toCategoryResForProductDetail())
                .deliveryPolicy(this.deliveryPolicy.toDTO())
                .mainImages(this.mainImages.stream().map(image -> image.toDTO()).toList())
                .options(this.getProductOptions().stream().map(option -> option.toDTO()).toList())
                .build();
    }

}
