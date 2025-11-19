package kr.kro.moonlightmoist.shopapi.category.repository;

import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class CategoryRepositoryUnitTest {

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        Category hairCare = Category.builder()
                .name("헤어케어")
                .depth(0)
                .displayOrder(5)
                .deleted(false)
                .build();

        categoryRepository.save(hairCare);

        Category shampooAndTreatment = Category.builder()
                .name("샴푸/트리트먼트")
                .parent(hairCare)
                .depth(1)
                .displayOrder(0)
                .deleted(false)
                .build();

        categoryRepository.save(shampooAndTreatment);

        Category hairOil = Category.builder()
                .name("헤어오일")
                .parent(hairCare)
                .depth(1)
                .displayOrder(1)
                .deleted(false)
                .build();

        categoryRepository.save(hairOil);

    }

    @Test
    @DisplayName("1개 카테고리 저장 테스트")
    public void saveCategory_Success() {

        Category category = Category.builder()
                .name("메이크업")
                .depth(0)
                .displayOrder(0)
                .deleted(false)
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("메이크업");
        assertThat(savedCategory.getDepth()).isEqualTo(0);
        assertThat(savedCategory.getDisplayOrder()).isEqualTo(0);
        assertThat(savedCategory.isDeleted()).isFalse();
        assertThat(savedCategory.getCreatedAt()).isNotNull();
        assertThat(savedCategory.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("존재 하지 않는 카테고리 조회 시 빈 Optional 반환")
    public void findByName_NotFound_ReturnsEmpty() {

        Optional<Category> category = categoryRepository.findByName("없는 카테고리");

        assertThat(category).isEmpty();
    }

    @Test
    @DisplayName("카테고리 이름으로 조회 후 부모카테고리 조회 테스트")
    public void findByName_And_getParentCategory() {
        Category hairOil = categoryRepository.findByName("헤어오일").get();
        Category hairCare = hairOil.getParent();

        assertThat(hairOil.getId()).isNotNull();
        assertThat(hairOil.getName()).isEqualTo("헤어오일");
        assertThat(hairOil.getDepth()).isEqualTo(1);
        assertThat(hairOil.getDisplayOrder()).isEqualTo(1);
        assertThat(hairOil.isDeleted()).isFalse();
        assertThat(hairOil.getCreatedAt()).isNotNull();
        assertThat(hairOil.getUpdatedAt()).isNotNull();

        assertThat(hairCare.getId()).isNotNull();
        assertThat(hairCare.getName()).isEqualTo("헤어케어");
        assertThat(hairCare.getDepth()).isEqualTo(0);
        assertThat(hairCare.getDisplayOrder()).isEqualTo(5);
        assertThat(hairCare.isDeleted()).isFalse();
        assertThat(hairCare.getCreatedAt()).isNotNull();
        assertThat(hairCare.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    public void updateCategoryName_Success() {
        Category hairCare = categoryRepository.findByName("헤어케어").get();
        hairCare.changeName("헤어케어2");

        Category foundHairCare = categoryRepository.findById(hairCare.getId()).get();
        assertThat(foundHairCare.getName()).isEqualTo("헤어케어2");
    }

    @Test
    @DisplayName("소프트 삭제 테스트")
    public void softDeleteCategory_Success() {
        Category category = categoryRepository.findByName("헤어케어").get();
        category.deleteCategory();
        categoryRepository.flush();

        Optional<Category> foundCategory = categoryRepository.findByName("헤어케어");

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().isDeleted()).isTrue();
    }

}
