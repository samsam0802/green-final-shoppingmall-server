package kr.kro.moonlightmoist.shopapi.product.repository;

import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.basicInfo.productName = :name")
    Optional<Product> findByProductName(@Param("name") String name);

    // 3차 카테고리 id 리스트로 조회
    Page<Product> findByCategoryIdIn(List<Long> categoryIds, Pageable pageable);
}
