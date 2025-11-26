package kr.kro.moonlightmoist.shopapi.product.service;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.repository.DeliveryPolicyRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductMainImage;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductImagesUrlDTO;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductOptionDTO;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRes;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final DeliveryPolicyRepository deliveryPolicyRepository;

    Product toEntity (ProductRequest dto) {

        Category category = categoryRepository.findById(dto.getCategory().getId()).get();
        Brand brand = brandRepository.findById(dto.getBrand().getId()).get();
        DeliveryPolicy deliveryPolicy = deliveryPolicyRepository.findById(dto.getDeliveryPolicy().getId()).get();

        return Product.builder()
                .category(category)
                .brand(brand)
                .basicInfo(dto.getBasicInfo().toDomain())
                .saleInfo(dto.getSaleInfo().toDomain())
                .deliveryPolicy(deliveryPolicy)
                .build();
    }

    @Override
    @Transactional
    public Long register(ProductRequest dto) {

        Product product = toEntity(dto);
        for(ProductOptionDTO optionDTO : dto.getOptions()) {
            ProductOption option = optionDTO.toDomain();
            product.addProductOption(option);
        }
        Product savedProduct = productRepository.save(product);
        System.out.println("savedProduct = " + savedProduct);

        return savedProduct.getId();
    }

    @Override
    @Transactional
    public void addImageUrls(Long id, ProductImagesUrlDTO dto) {

        Product product = productRepository.findById(id).get();

        for(int i=0; i<dto.getOptionImageUrls().size(); i++) {
            product.getProductOptions().get(i).setImageUrl(dto.getOptionImageUrls().get(i));
        }
        System.out.println("product.getMainImages() = " + product.getMainImages());
        for(int i=0; i<dto.getMainImageUrls().size(); i++) {
            System.out.println("mainImage url 저장");
            product.getMainImages()
                    .add(ProductMainImage.builder()
                            .imageUrl(dto.getMainImageUrls().get(i))
                            .build()
                    );
        }
        System.out.println("product.getMainImages() = " + product.getMainImages());
    }

    @Override
    public List<ProductRes> searchProductsByCategory(List<Long> depth3CategoryIds) {

        Pageable pageable = PageRequest.of(
                0,
                24,
                Sort.by("id").descending()
        );

        Page<Product> page = productRepository.findByCategoryIdIn(depth3CategoryIds, pageable);

        List<ProductRes> result = page.get().map(product -> ProductRes.builder()
                .id(product.getId())
                .brand(product.getBrand().toDTO())
                .category(product.getCategory().toDTO())
                .basicInfo(product.getBasicInfo().toDTO())
                .saleInfo(product.getSaleInfo().toDTO())
                .deliveryPolicy(product.getDeliveryPolicy().toDTO())
                .options(product.getProductOptions().stream().map(option -> option.toDTO()).toList())
                .mainImages(product.getMainImages().stream().map(image -> image.toDTO()).toList())
                .build()).toList();

        return result;
    }


}
