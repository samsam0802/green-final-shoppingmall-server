package kr.kro.moonlightmoist.shopapi.product.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "search_histories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long userId;

    @Column(nullable = true)
    private String sessionIdentifier;

    @Column(nullable = false, length = 150)
    private String keyword;
}
