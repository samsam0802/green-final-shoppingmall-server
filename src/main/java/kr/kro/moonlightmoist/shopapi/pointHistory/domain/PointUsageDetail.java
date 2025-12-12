package kr.kro.moonlightmoist.shopapi.pointHistory.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "point_usage_details")
public class PointUsageDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_point_history_id")
    private PointHistory usedPointHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earned_point_history_id")
    private PointHistory earnedPointHistory;

    private int usedAmount;
}
