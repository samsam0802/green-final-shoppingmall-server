package kr.kro.moonlightmoist.shopapi.brand.repository;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import org.assertj.core.api.Assertions;
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
class BrandRepositoryUnitTest {

    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    public void init() {
        Brand brand = Brand.builder()
                .name("이니스프리")
                .deleted(false)
                .build();

        brandRepository.save(brand);
    }

    @Test
    @DisplayName("브랜드 저장 테스트")
    public void saveBrand_Success() {
        Brand brand = Brand.builder()
                .name("스킨푸드")
                .deleted(false)
                .build();

        Brand savedBrand = brandRepository.save(brand);
        assertThat(savedBrand.getId()).isNotNull();
        assertThat(savedBrand.getName()).isEqualTo("스킨푸드");
        assertThat(savedBrand.isDeleted()).isFalse();
        assertThat(savedBrand.getCreatedAt()).isNotNull();
        assertThat(savedBrand.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("브랜드 이름으로 조회 테스트")
    public void findByName_Success() {
        Brand brand = brandRepository.findByName("이니스프리").get();

        assertThat(brand.getId()).isNotNull();
        assertThat(brand.getName()).isEqualTo("이니스프리");
        assertThat(brand.isDeleted()).isFalse();
        assertThat(brand.getCreatedAt()).isNotNull();
        assertThat(brand.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("브랜드 이름수정 테스트")
    public void changeName_Success() {
        Brand brand = brandRepository.findByName("이니스프리").get();
        brand.changeName("헤라");

        Brand changedBrand = brandRepository.findById(brand.getId()).get();
        assertThat(changedBrand.getName()).isEqualTo("헤라");
    }

    @Test
    @DisplayName("소프트 삭제 테스트")
    public void softDelete_Success() {
        Brand brand = brandRepository.findByName("이니스프리").get();
        brand.deleteBrand();

        Brand deletedBrand = brandRepository.findById(brand.getId()).get();
        assertThat(deletedBrand.isDeleted()).isTrue();
    }

}
