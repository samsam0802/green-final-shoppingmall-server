package kr.kro.moonlightmoist.shopapi.review.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table(name="review_comments")
public class ReviewComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(name="is_visible", nullable = false)
    @Builder.Default
    private boolean visible = true;

    @Column(name="is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public void changeContent(String content) { this.content = content; }
    public void changeDeleted(boolean deleted) { this.deleted = deleted; }
    public void changeVisible(boolean visible) { this.visible = visible; }

}
