package kr.kro.moonlightmoist.shopapi.brand.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Table(name = "brands")
public class Brand extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

    public void changeName(String name) {
        this.name = name;
    }

    public void deleteBrand() {
        this.deleted = true;
    }

}
