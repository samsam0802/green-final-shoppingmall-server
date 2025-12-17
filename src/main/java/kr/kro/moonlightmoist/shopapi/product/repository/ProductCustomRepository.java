package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> search(ProductSearchCondition condition);
    Page<Product> searchByConditionWithPaging(ProductSearchCondition condition, Pageable pageable);

    // 상품 카테고리별 조회 커스텀 메서드
    Page<Product> findByCategoriesAndBrand(List<Long> categoryIds, Long brandId, Pageable pageable);
}
