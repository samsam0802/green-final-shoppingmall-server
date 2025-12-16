package kr.kro.moonlightmoist.shopapi.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.brand.domain.QBrand;
import kr.kro.moonlightmoist.shopapi.category.domain.QCategory;
import kr.kro.moonlightmoist.shopapi.product.domain.*;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ProductCustomRepositoryImpl implements ProductCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ProductCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Product> search(ProductSearchCondition condition) {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        List<Product> productList = queryFactory
                .selectFrom(product)
                .join(product.category, category)
                .where(
                        productNameFilter(condition.getProductName()),
                        searchKeywordFilter(condition.getSearchKeywords()),
                        categoryFilter(condition.getCategoryIds()),
                        saleStatusFilter(condition.getSaleStatuses()),
                        exposureStatusFilter(condition.getExposureStatuses()),
                        dateFilter(
                                condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null,
                                condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null
                        )
                )
                .fetch();

        return productList;
    }

    @Override
    public Page<Product> searchByConditionWithPaging(ProductSearchCondition condition, Pageable pageable) {

        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        JPAQuery<Product> contentQuery = queryFactory
                .selectFrom(product)
                .join(product.category, category)
                .where(
                        productNameFilter(condition.getProductName()),
                        searchKeywordFilter(condition.getSearchKeywords()),
                        categoryFilter(condition.getCategoryIds()),
                        saleStatusFilter(condition.getSaleStatuses()),
                        exposureStatusFilter(condition.getExposureStatuses()),
                        dateFilter(
                                condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null,
                                condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null
                        )
                );

        // 페이징 처리
        contentQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 정렬 처리 안넣음

        // 쿼리 실행 결과
        List<Product> content = contentQuery.fetch();

        // Count 조회 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .join(product.category, category)
                .where(
                        productNameFilter(condition.getProductName()),
                        searchKeywordFilter(condition.getSearchKeywords()),
                        categoryFilter(condition.getCategoryIds()),
                        saleStatusFilter(condition.getSaleStatuses()),
                        exposureStatusFilter(condition.getExposureStatuses()),
                        dateFilter(
                                condition.getStartDate() != null ? condition.getStartDate().atStartOfDay() : null,
                                condition.getEndDate() != null ? condition.getEndDate().atTime(LocalTime.MAX) : null
                        )
                );

        // Page 객체로 변환
        Page<Product> page = PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
        return page;
    }

    private BooleanExpression productNameFilter(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            return null;
        }

        String[] keywords = productName.trim().split("\\s+");
        BooleanExpression expression = null;

        for (String keyword : keywords) {
            if(!keyword.trim().isEmpty()) {
                BooleanExpression current = QProduct.product.basicInfo.productName.contains(keyword);
                expression = expression == null ? current : expression.or(current);
            }
        }

        return expression;
    }

    private BooleanExpression searchKeywordFilter(String searchKeyword) {
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return null;
        }

        String[] keywords = searchKeyword.trim().split("\\s+");
        BooleanExpression expression = null;

        for (String keyword : keywords) {
            if(!keyword.trim().isEmpty()) {
                BooleanExpression current = QProduct.product.basicInfo.searchKeywords.contains(keyword);
                expression = expression == null ? current : expression.or(current);
            }
        }

        return expression;
    }

    private BooleanExpression categoryFilter(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return null;
        }
        QProduct product = QProduct.product;
        return product.category.id.in(categoryIds);
    }

    private BooleanExpression dateFilter(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        if (startDateTime == null && endDateTime == null) {
            return null;
        }

        QProduct product = QProduct.product;

        if (startDateTime != null && endDateTime != null) {
            return product.createdAt.between(startDateTime, endDateTime);
        } else if (startDateTime != null) {
            return product.createdAt.goe(startDateTime);
        } else if (endDateTime != null) {
            return product.createdAt.loe(endDateTime);
        }

        return null;
    }

    private BooleanExpression saleStatusFilter(List<SaleStatus> saleStatuses) {
        if (saleStatuses == null || saleStatuses.isEmpty()) {
            return null;
        }
        if (saleStatuses.containsAll(List.of(SaleStatus.values()))) {
            return null;
        }

        QProduct product = QProduct.product;
        return product.saleInfo.saleStatus.in(saleStatuses);
    }

    private BooleanExpression exposureStatusFilter(List<ExposureStatus> exposureStatuses) {
        if (exposureStatuses == null || exposureStatuses.isEmpty()) {
            return null;
        }
        if (exposureStatuses.containsAll(List.of(ExposureStatus.values()))) {
            return null;
        }

        QProduct product = QProduct.product;
        return product.saleInfo.exposureStatus.in(exposureStatuses);
    }


}
