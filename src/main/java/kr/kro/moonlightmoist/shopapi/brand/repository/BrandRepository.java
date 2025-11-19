package kr.kro.moonlightmoist.shopapi.brand.repository;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String name);
}
