package kr.kro.moonlightmoist.shopapi.pointHistory.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Setter
@Table(name = "point_histories")
public class PointHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    private Integer pointValue;
    private Integer remainingPoint;
    private LocalDateTime expiredAt;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean deleted = false;

}
