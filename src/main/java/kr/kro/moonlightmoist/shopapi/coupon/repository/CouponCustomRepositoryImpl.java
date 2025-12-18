package kr.kro.moonlightmoist.shopapi.coupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.coupon.domain.*;
import kr.kro.moonlightmoist.shopapi.coupon.dto.CouponSearchCondition;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CouponCustomRepositoryImpl implements CouponCustomRepository{

    private final JPAQueryFactory queryFactory;

    public CouponCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Coupon> search(CouponSearchCondition condition) {
        QCoupon coupon = QCoupon.coupon;
        List<Coupon> coupons = queryFactory.selectFrom(coupon)
                .where(
                        nameFilter(condition.getName()),
                        couponCodeFilter(condition.getCouponCode()),
                        issueTypeFilter(condition.getIssueType()),
                        discountTypeFilter(condition.getDiscountType()),
                        availabilityFilter(condition.getAvailability()),
                        dateFilter(
                                condition.getCreatedAtFrom() == null ? null : condition.getCreatedAtFrom().atStartOfDay(),
                                condition.getCreatedAtTo() == null ? null : condition.getCreatedAtTo().atTime(LocalTime.MAX)
                        ),
                        coupon.deleted.isFalse()
                )
                .fetch();
        return coupons;
    }

    public BooleanExpression nameFilter(String name) {
        if( name == null || name.trim().isEmpty()) {
            return null;
        }

        String[] keywords = name.trim().split("\\s+");
        BooleanExpression expression = null;

        for (String keyword : keywords) {
            BooleanExpression current = QCoupon.coupon.name.contains(keyword);
            expression = expression == null ? current : expression.or(current);
        }
        return expression;
    }

    public BooleanExpression couponCodeFilter(String couponCode) {
        if (couponCode == null || couponCode.trim().isEmpty()) {
            return null;
        }

        String[] keywords = couponCode.trim().split("\\s+");
        BooleanExpression expression = null;
        for (String keyword : keywords) {
            BooleanExpression current = QCoupon.coupon.couponCode.contains(keyword);
            expression = expression == null ? current : expression.or(current);
        }
        return expression;
    }

    public BooleanExpression issueTypeFilter(List<IssueType> issueTypes) {
        if (issueTypes == null || issueTypes.isEmpty() || issueTypes.containsAll(List.of(IssueType.values()))) {
            return null;
        }

        BooleanExpression expression = QCoupon.coupon.issueType.in(issueTypes);
        return expression;
    }

    public BooleanExpression discountTypeFilter(List<DiscountType> discountTypes) {
        if (discountTypes == null || discountTypes.isEmpty() || discountTypes.containsAll(List.of(DiscountType.values()))) {
            return null;
        }

        BooleanExpression expression = QCoupon.coupon.discountType.in(discountTypes);
        return expression;
    }

    public BooleanExpression availabilityFilter (List<CouponAvailability> availabilities) {
        if (availabilities == null || availabilities.isEmpty() || availabilities.containsAll(List.of(CouponAvailability.values()))) {
            return null;
        }

        BooleanExpression expression = QCoupon.coupon.availability.in(availabilities);
        return expression;
    }

    public BooleanExpression dateFilter(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null && endTime == null) {
            return null;
        }
        QCoupon coupon = QCoupon.coupon;

        if (startTime != null && endTime != null) {
            return coupon.createdAt.between(startTime, endTime);
        } else if (startTime != null) {
            return coupon.createdAt.goe(startTime);
        } else if (endTime != null) {
            return coupon.createdAt.loe(endTime);
        }

        return null;
    }
}
