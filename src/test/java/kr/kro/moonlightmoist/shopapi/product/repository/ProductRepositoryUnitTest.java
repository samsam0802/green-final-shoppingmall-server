package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.*;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class ProductRepositoryUnitTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Brand brand;
    private Category category;

    @BeforeEach
    public void init() {
        brand = brandRepository.save(EntityFactory.createBrand("이니스프리"));
        category = categoryRepository.save(EntityFactory.createCategory("헤어케어", 0, 0));

        Product product = EntityFactory.createProduct(category, brand);
        productRepository.save(product);
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void saveProduct_Success() {

        ProductMainImage mainImage1 = ProductMainImage.builder()
                .imageUrl("mainimageUrl1")
                .imageType(ImageType.THUMBNAIL)
                .build();
        ProductMainImage mainImage2 = ProductMainImage.builder()
                .imageUrl("mainimageUrl2")
                .imageType(ImageType.GALLERY)
                .build();

        ProductDetailImage detailImage = ProductDetailImage.builder()
                .imageUrl("deailimageUrl")
                .build();

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .basicInfo(BasicInfo.builder()
                        .productName("상품")
                        .productCode("코드")
                        .searchKeywords("키워드")
                        .description("설명")
                        .build())
                .saleInfo(SaleInfo.builder()
                        .exposureStatus(ExposureStatus.EXPOSURE)
                        .saleStatus(SaleStatus.ON_SALE)
                        .cancelable(true)
                        .build())
                .deleted(false)
                .build();

        product.addMainImage(mainImage1);
        product.addMainImage(mainImage2);
        product.addDetailImage(detailImage);

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getCategory()).isNotNull();
        assertThat(savedProduct.getBrand()).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getBasicInfo().getProductName()).isEqualTo("상품");
        assertThat(savedProduct.getBasicInfo().getProductCode()).isEqualTo("코드");
        assertThat(savedProduct.getBasicInfo().getSearchKeywords()).isEqualTo("키워드");
        assertThat(savedProduct.getBasicInfo().getDescription()).isEqualTo("설명");
        assertThat(savedProduct.getSaleInfo().getExposureStatus()).isEqualTo(ExposureStatus.EXPOSURE);
        assertThat(savedProduct.getSaleInfo().getSaleStatus()).isEqualTo(SaleStatus.ON_SALE);
        assertThat(savedProduct.getSaleInfo().isCancelable()).isTrue();
        assertThat(savedProduct.isDeleted()).isFalse();
        assertThat(savedProduct.getCreatedAt()).isNotNull();
        assertThat(savedProduct.getUpdatedAt()).isNotNull();
        assertThat(savedProduct.getMainImages().size()).isEqualTo(2);
        assertThat(savedProduct.getDetailImages().size()).isEqualTo(1);
        assertThat(savedProduct.getDetailImages().get(0).getImageUrl()).isEqualTo("deailimageUrl");
    }

    // 동일한 상품이름 or 상품코드로 중복 저장 테스트

    // 조회 테스트
    @Test
    @DisplayName("단일 상품 이름으로 조회 테스트")
    public void findByProductName_Success() {
        Product product = productRepository.findByProductName("임시상품").get();

        assertThat(product.getBasicInfo().getProductCode()).isEqualTo("임시상품코드");
        assertThat(product.getBasicInfo().getSearchKeywords()).isEqualTo("임시키워드");
        assertThat(product.getBasicInfo().getDescription()).isEqualTo("임시상품설명");
        assertThat(product.getSaleInfo().getExposureStatus()).isEqualTo(ExposureStatus.EXPOSURE);
        assertThat(product.getSaleInfo().getSaleStatus()).isEqualTo(SaleStatus.ON_SALE);
        assertThat(product.getSaleInfo().isCancelable()).isTrue();
        assertThat(product.isDeleted()).isFalse();
        assertThat(product.getCategory()).isNotNull();
        assertThat(product.getBrand()).isNotNull();

    }

    // 수정 테스트
//    @Test
//    @DisplayName("상품 이름 수정 테스트")
//    public void changeProductName_Success() {
//        Product product = productRepository.findAll().get(0);
//        product.getBasicInfo().changeProductName("상품이름수정");
//
//        Product foundProduct = productRepository.findById(product.getId()).get();
//
//        assertThat(foundProduct.getProductName()).isEqualTo("상품이름수정");
//    }

    // 삭제 테스트
    @Test
    @DisplayName("상품 소프트 삭제 테스트")
    public void deleteProduct_Success() {
        Product product = productRepository.findAll().get(0);
        product.deleteProduct();

        Product foundProduct = productRepository.findById(product.getId()).get();

        assertThat(foundProduct.isDeleted()).isTrue();
    }

}
