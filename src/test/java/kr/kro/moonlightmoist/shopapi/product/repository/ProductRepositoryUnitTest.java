package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
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

    @BeforeEach
    public void init() {
        Brand brand = EntityFactory.createBrand("이니스프리");
        brandRepository.save(brand);

        Category category = EntityFactory.createCategory("헤어케어", 0, 0);
        categoryRepository.save(category);

        Product product = EntityFactory.createProduct(category, brand);
        productRepository.save(product);
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void saveProduct_Success() {
        Brand brand = brandRepository.findAll().get(0);
        Category category = categoryRepository.findAll().get(0);

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .productName("상품")
                .productCode("코드")
                .searchKeywords("키워드")
                .description("설명")
                .exposureStatus(ExposureStatus.EXPOSURE)
                .saleStatus(SaleStatus.ON_SALE)
                .cancelable(true)
                .deleted(false)
                .build();

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getCategory()).isNotNull();
        assertThat(savedProduct.getBrand()).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("상품");
        assertThat(savedProduct.getProductCode()).isEqualTo("코드");
        assertThat(savedProduct.getSearchKeywords()).isEqualTo("키워드");
        assertThat(savedProduct.getDescription()).isEqualTo("설명");
        assertThat(savedProduct.getExposureStatus()).isEqualTo(ExposureStatus.EXPOSURE);
        assertThat(savedProduct.getSaleStatus()).isEqualTo(SaleStatus.ON_SALE);
        assertThat(savedProduct.isCancelable()).isTrue();
        assertThat(savedProduct.isDeleted()).isFalse();
        assertThat(savedProduct.getCreatedAt()).isNotNull();
        assertThat(savedProduct.getUpdatedAt()).isNotNull();
    }

    // 동일한 상품이름 or 상품코드로 중복 저장 테스트

    // 조회 테스트
    @Test
    @DisplayName("단일 상품 이름으로 조회 테스트")
    public void findByProductName_Success() {
        Product product = productRepository.findByProductName("임시상품").get();

        assertThat(product.getProductCode()).isEqualTo("임시상품코드");
        assertThat(product.getSearchKeywords()).isEqualTo("임시키워드");
        assertThat(product.getDescription()).isEqualTo("임시상품설명");
        assertThat(product.getExposureStatus()).isEqualTo(ExposureStatus.EXPOSURE);
        assertThat(product.getSaleStatus()).isEqualTo(SaleStatus.ON_SALE);
        assertThat(product.isCancelable()).isTrue();
        assertThat(product.isDeleted()).isFalse();
        assertThat(product.getCategory()).isNotNull();
        assertThat(product.getBrand()).isNotNull();

    }

    // 수정 테스트
    @Test
    @DisplayName("상품 이름 수정 테스트")
    public void changeProductName_Success() {
        Product product = productRepository.findAll().get(0);
        product.changeProductName("상품이름수정");

        Product foundProduct = productRepository.findById(product.getId()).get();

        assertThat(foundProduct.getProductName()).isEqualTo("상품이름수정");
    }

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
