package kr.kro.moonlightmoist.shopapi.category.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryResForList;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryResForProductDetail;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"parent", "subCategories"})
@Getter
@Builder
@Table(name = "categories")
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private List<Category> subCategories = new ArrayList<>();

    @Column(unique = false, nullable = false)
    private String name;

    @Column(nullable = false)
    private int depth;

    @Column(nullable = false)
    private int displayOrder;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    public void changeName(String name) {
        this.name = name;
    }

    public void deleteCategory() {
        this.deleted = true;
    }

    public void recoverCategory() {
        this.deleted = false;
    }

//    toDTO 가 subScategories 도 DTO 로 바꿔줘야 함
    public CategoryResForList toCategoryResForList() {
        return CategoryResForList.builder()
                .id(this.id)
                .subCategories(this.subCategories.stream().map(category -> category.toCategoryResForList()).toList())
                .name(this.name)
                .depth(this.depth)
                .displayOrder(this.displayOrder)
                .build();
    }

    public CategoryResForProductDetail toCategoryResForProductDetail() {

        CategoryResForProductDetail parent;
        if(this.parent == null) {
            parent = new CategoryResForProductDetail();
        } else {
            parent = this.parent.toCategoryResForProductDetail();
        }

        return CategoryResForProductDetail.builder()
                .id(this.id)
                .parent(parent)
                .name(this.name)
                .depth(this.depth)
                .displayOrder(this.displayOrder)
                .build();
    }

}
