package kr.kro.moonlightmoist.shopapi.common.domain;

import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class BaseTimeEntityUnitTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("생성일자 자동 주입 테스트")
    public void baseTimeEntity_createdAt_Test() {
        Category category = Category.builder()
                .name("메이크업")
                .depth(0)
                .displayOrder(0)
                .deleted(false)
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getCreatedAt()).isNotNull();
        assertThat(savedCategory.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("수정일자 자동 업데이트 테스트")
    public void baseTimeEntity_updatedAt_Test() {
        Category category = Category.builder()
                .name("메이크업")
                .depth(0)
                .displayOrder(0)
                .deleted(false)
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getCreatedAt()).isNotNull();
        assertThat(savedCategory.getUpdatedAt()).isNotNull();
        LocalDateTime firstUpdateAt = savedCategory.getUpdatedAt();

        categoryRepository.flush();
        Category foundCategory = categoryRepository.findByName("메이크업").get();
        foundCategory.changeName("메이크업수정");
        categoryRepository.flush();
        Category foundCategory2 = categoryRepository.findByName("메이크업수정").get();

        assertThat(foundCategory2.getUpdatedAt()).isNotEqualTo(firstUpdateAt);
    }

}