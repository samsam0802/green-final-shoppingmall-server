package kr.kro.moonlightmoist.shopapi.restockNoti.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "restock_notifications",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_user_product_option",
                    columnNames = {"user_id", "product_option_id"}
            )
        }
)
public class RestockNotification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private NotificationStatus notificationStatus = NotificationStatus.WAITING;

    private LocalDateTime notifiedAt;

    @Column(name = "is_deleted")
    private boolean deleted;


}
