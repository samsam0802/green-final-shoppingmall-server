package kr.kro.moonlightmoist.shopapi.coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.kro.moonlightmoist.shopapi.coupon.domain.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponDto {
    private Long id;
    @JsonProperty("couponName")
    private String name;
    private IssueType issueType;
    private AutoIssueType autoIssueType;
    private AutoIssueTrigger autoIssueTrigger;
    private String couponCode;
    private Long totalQuantity;
    private LocalDateTime issuableStartDate;
    private LocalDateTime issuableEndDate;
    private CouponAvailability availability;
    @JsonProperty("hasLimitUsagePeriod")
    private Boolean limitUsagePeriod;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    @JsonProperty("hasLimitMinOrder")
    private Boolean limitMinOrderAmount;
    private Integer minOrderAmount;
    private DiscountType discountType;
    private Integer fixedDiscountAmount;
    private Integer discountPercentage;
    @JsonProperty("hasLimitMaxDiscount")
    private Boolean limitMaxDiscountAmount;
    private Integer maxDiscountAmount;
    private Integer issueCount;
    private Boolean deleted;

    public Coupon toEntity() {
        return Coupon.builder()
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
