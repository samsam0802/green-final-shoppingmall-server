package kr.kro.moonlightmoist.shopapi.review.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Table(name="reviews")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Check(constraints = "rating in(1,2,3,4,5)")
    private int rating;

    @Column(name="is_visible", nullable = false)
    @Builder.Default
    private boolean visible = true;

    @Column(name="is_deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ElementCollection
    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
    @Builder.Default
    private List<ReviewImage> reviewImages = new ArrayList<>();

    public void addImage(ReviewImage image) {
        image.setImageOrder(this.reviewImages.size());
        reviewImages.add(image);
    }

    public void removeImgUrls(List<String> urls) {
        this.reviewImages.removeIf(img -> urls.contains(img.getImageUrl()));
    }

    public void changeContent(String content) { this.content=content; }
    public void changeRating(int rating) { this.rating=rating; }

}
