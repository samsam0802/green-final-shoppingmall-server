package kr.kro.moonlightmoist.shopapi.category.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int depth;

    @Column(nullable = false)
    private int displayOrder;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void changeName(String name) {
        this.name = name;
    }

    public void deleteCategory() {
        this.deleted = true;
    }

    public void recoverCategory() {
        this.deleted = false;
    }

}
