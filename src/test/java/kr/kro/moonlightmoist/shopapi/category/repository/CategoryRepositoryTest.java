package kr.kro.moonlightmoist.shopapi.category.repository;

import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        Category hairCare = Category.builder()
                .name("헤어케어")
                .depth(0)
                .displayOrder(5)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        categoryRepository.save(hairCare);

        Category shampooAndTreatment = Category.builder()
                .name("샴푸/트리트먼트")
                .parent(hairCare)
                .depth(1)
                .displayOrder(0)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        categoryRepository.save(shampooAndTreatment);

        Category hairOil = Category.builder()
                .name("헤어오일")
                .parent(hairCare)
                .depth(1)
                .displayOrder(1)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        categoryRepository.save(hairOil);

    }

    @Test
    @DisplayName("1개 카테고리 저장 테스트")
    public void insert1Test() {

        Category category = Category.builder()
                .name("메이크업")
                .depth(0)
                .displayOrder(0)
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("메이크업");
        assertThat(savedCategory.getDepth()).isEqualTo(0);
        assertThat(savedCategory.getDisplayOrder()).isEqualTo(0);
        assertThat(savedCategory.isDeleted()).isEqualTo(false);
        assertThat(savedCategory.getCreatedAt()).isNotNull();
        assertThat(savedCategory.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("카테고리 조회 테스트")
    public void selectTest() {
        Category hairOil = categoryRepository.findByName("헤어오일").get();
        Category hairCare = hairOil.getParent();

        assertThat(hairOil.getId()).isNotNull();
        assertThat(hairOil.getName()).isEqualTo("헤어오일");
        assertThat(hairOil.getDepth()).isEqualTo(1);
        assertThat(hairOil.getDisplayOrder()).isEqualTo(1);
        assertThat(hairOil.isDeleted()).isEqualTo(false);
        assertThat(hairOil.getCreatedAt()).isNotNull();
        assertThat(hairOil.getUpdatedAt()).isNotNull();

        assertThat(hairCare.getId()).isNotNull();
        assertThat(hairCare.getName()).isEqualTo("헤어케어");
        assertThat(hairCare.getDepth()).isEqualTo(0);
        assertThat(hairCare.getDisplayOrder()).isEqualTo(5);
        assertThat(hairCare.isDeleted()).isEqualTo(false);
        assertThat(hairCare.getCreatedAt()).isNotNull();
        assertThat(hairCare.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    public void updateTest() {
        Category hairCare = categoryRepository.findByName("헤어케어").get();
        hairCare.changeName("헤어케어2");

        Category foundHairCare = categoryRepository.findById(hairCare.getId()).get();
        assertThat(foundHairCare.getName()).isEqualTo("헤어케어2");
    }

    @Test
    @DisplayName("소프트 삭제 테스트")
    public void softDeleteTest() {
        Category category = categoryRepository.findByName("헤어케어").get();
        category.deleteCategory();

        assertThat(category.isDeleted()).isTrue();

    }
}