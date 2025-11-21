package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    Product toEntity (ProductRequest dto) {

        Category category = categoryRepository.findById(dto.getCategory().getId()).get();
        Brand brand = brandRepository.findById(dto.getBrand().getId()).get();

        return Product.builder()
                .category(category)
                .brand(brand)
                .productName(dto.getProductName())
                .productCode("temp")
                .searchKeywords(dto.getSearchKeywords())
                .exposureStatus(dto.getExposureStatus())
                .saleStatus(dto.getSaleStatus())
                .description(dto.getDescription())
                .cancelable(dto.isCancelable())
                .deleted(dto.isDeleted())
                .build();
    }

    @Override
    public Long register(ProductRequest dto) {

        Product entity = toEntity(dto);

        Product savedProduct = productRepository.save(entity);

        System.out.println("savedProduct = " + savedProduct);

        return savedProduct.getId();
    }
}
