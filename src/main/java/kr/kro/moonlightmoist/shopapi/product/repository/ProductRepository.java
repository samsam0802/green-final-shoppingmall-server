package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>
    , ProductCustomRepository
{
    @Query("SELECT p FROM Product p WHERE p.basicInfo.productName = :name")
    Optional<Product> findByProductName(@Param("name") String name);

    // 3차 카테고리 id 리스트로 조회
    Page<Product> findByCategoryIdIn(List<Long> categoryIds, Pageable pageable);

    // 가격정렬 : 최소가격부터
    @Query(value = """
            SELECT p.*
            FROM products p
            LEFT JOIN product_options o ON p.id = o.product_id
            WHERE p.category_id IN :categoryIds
            GROUP BY p.id
            ORDER BY MIN(o.selling_price) ASC
            """,
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM products p WHERE p.category_id IN :categoryIds",
            nativeQuery = true
    )
    Page<Product> findByCategoryIdOrderByMinPrice(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    // 가격정렬 : 최고가격부터
    @Query(value = """
            SELECT p.*
            FROM products p
            LEFT JOIN product_options o ON p.id = o.product_id
            WHERE p.category_id IN :categoryIds
            GROUP BY p.id
            ORDER BY MIN(o.selling_price) DESC
            """,
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM products p WHERE p.category_id IN :categoryIds",
            nativeQuery = true
    )
    Page<Product> findByCategoryIdOrderByMaxPrice(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

}
