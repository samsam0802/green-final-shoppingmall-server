package kr.kro.moonlightmoist.shopapi.coupon.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "coupons")
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private IssueType issueType;

    @Column(nullable = true)
    private AutoIssueType autoIssueType;

    @Column(nullable = true)
    private AutoIssueTrigger autoIssueTrigger;

    @Column(nullable = true)
    private String couponCode;

    @Column(nullable = true)
    private Long totalQuantity;

    @Column(nullable = false)
    private LocalDate issuableStartDate;

    @Column(nullable = false)
    private LocalDate issuableEndDate;

    @Column(nullable = false)
    private CouponAvailability availability;

    @Column(name = "has_limit_usage_period", nullable = false)
    private Boolean limitUsagePeriod;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false)
    private LocalDate validTo;

    @Column(name = "has_limit_min_order_amount", nullable = false)
    private Boolean limitMinOrderAmount;

    @Column(nullable = true)
    private Integer minOrderAmount;

    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = true)
    private Integer fixedDiscountAmount;

    @Column(nullable = true)
    private Integer discountPercentage;

    @Column(name = "has_limit_max_discount_amount", nullable = true)
    private Boolean limitMaxDiscountAmount;

    @Column(nullable = true)
    private Integer maxDiscountAmount;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    public CouponDto toDto() {
        return CouponDto.builder()
                .id(this.id)
                .name(this.name)
                .issueType(this.issueType)
                .autoIssueType(this.autoIssueType)
                .autoIssueTrigger(this.autoIssueTrigger)
                .couponCode(this.couponCode)
                .totalQuantity(this.totalQuantity)
                .issuableStartDate(this.issuableStartDate)
                .issuableEndDate(this.issuableEndDate)
                .availability(this.availability)
                .limitUsagePeriod(this.limitUsagePeriod)
                .validFrom(this.validFrom)
                .validTo(this.validTo)
                .limitMinOrderAmount(this.limitMinOrderAmount)
                .minOrderAmount(this.minOrderAmount)
                .discountType(this.discountType)
                .fixedDiscountAmount(this.fixedDiscountAmount)
                .discountPercentage(this.discountPercentage)
                .limitMaxDiscountAmount(this.limitMaxDiscountAmount)
                .maxDiscountAmount(this.maxDiscountAmount)
                .build();
    }
}
