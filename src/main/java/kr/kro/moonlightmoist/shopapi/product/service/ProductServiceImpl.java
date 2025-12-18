package kr.kro.moonlightmoist.shopapi.product.service;

import jakarta.persistence.EntityNotFoundException;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.repository.DeliveryPolicyRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.*;
import kr.kro.moonlightmoist.shopapi.product.dto.*;
import kr.kro.moonlightmoist.shopapi.product.repository.DetailInfoRepository;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final DeliveryPolicyRepository deliveryPolicyRepository;
    private final DetailInfoRepository detailInfoRepository;

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
                .detailInfo(dto.getDetailInfo().toEntity())
                .build();
    }

    @Override
    @Transactional
    public Long register(ProductRequest dto) {

        Product product = toEntity(dto);
        for(ProductOptionDTO optionDTO : dto.getOptions()) {
            ProductOption option = optionDTO.toEntity();
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
            product.getProductOptions().get(i).setDisplayOrder(i);
        }
        System.out.println("product.getMainImages() = " + product.getMainImages());
        for(int i=0; i<dto.getMainImageUrls().size(); i++) {
            System.out.println("mainImage url 저장");
            product.getMainImages()
                    .add(ProductMainImage.builder()
                            .imageUrl(dto.getMainImageUrls().get(i))
                            .imageType(i==0 ? ImageType.THUMBNAIL : ImageType.GALLERY)
                            .displayOrder(i)
                            .build()
                    );
        }
        System.out.println("product.getMainImages() = " + product.getMainImages());
        for(int i=0; i<dto.getDetailImageUrls().size(); i++) {
            System.out.println("detailImage url 저장");
            product.getDetailImages()
                    .add(ProductDetailImage.builder()
                            .imageUrl(dto.getDetailImageUrls().get(i))
                            .displayOrder(i)
                            .build()
                    );
        }
        System.out.println("product.getDetailImages() = " + product.getDetailImages());
    }

    @Override
    public PageResponseDTO<ProductResForList> searchProductsByCategory(List<Long> depth3CategoryIds,Long brandId, PageRequestDTO pageRequest) {

        Pageable pageable = null;
        Page<Product> page = null;

        if (pageRequest.getSort() == null || pageRequest.getSort().equals("sales")) {
            // 판매순
            pageable = PageRequest.of(
                    pageRequest.getPage()-1,
                    pageRequest.getSize(),
                    Sort.by("saleInfo.totalSalesCount").descending()
            );
//            page = productRepository.findByCategoryIdIn(depth3CategoryIds, pageable);
            page = productRepository.findByCategoriesAndBrand(depth3CategoryIds, brandId, pageable);
        } else if (pageRequest.getSort().equals("price_asc")) {
            pageable = PageRequest.of(
                    pageRequest.getPage()-1,
                    pageRequest.getSize(),
                    Sort.by("price").ascending()
            );
//            page = productRepository.findByCategoryIdOrderByMinPrice(depth3CategoryIds, pageable);
            page = productRepository.findByCategoriesAndBrandOrderByPrice(depth3CategoryIds, brandId, pageable);
        } else if (pageRequest.getSort().equals("price_desc")) {
            pageable = PageRequest.of(
                    pageRequest.getPage()-1,
                    pageRequest.getSize(),
                    Sort.by("price").descending()
            );
//            page = productRepository.findByCategoryIdOrderByMaxPrice(depth3CategoryIds, pageable);
            page = productRepository.findByCategoriesAndBrandOrderByPrice(depth3CategoryIds, brandId, pageable);
        } else if (pageRequest.getSort().equals("latest")) {
            pageable = PageRequest.of(
                    pageRequest.getPage()-1,
                    pageRequest.getSize(),
                    Sort.by("id").descending()
            );
//            page = productRepository.findByCategoryIdIn(depth3CategoryIds, pageable);
            page = productRepository.findByCategoriesAndBrand(depth3CategoryIds, brandId, pageable);
        } else {
            // 인기순 (아직 구현 x, 아래는 임시 코드 )
            pageable = PageRequest.of(
                    pageRequest.getPage()-1,
                    pageRequest.getSize(),
                    Sort.by("saleInfo.totalSalesCount").descending()
            );
//            page = productRepository.findByCategoryIdIn(depth3CategoryIds, pageable);
            page = productRepository.findByCategoriesAndBrand(depth3CategoryIds, brandId, pageable);
        }

        List<ProductResForList> dtoList = page.get().map(product -> product.toDTOForList()).toList();

        PageResponseDTO<ProductResForList> response = PageResponseDTO.<ProductResForList>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequest)
                .totalDataCount(page.getTotalElements())
                .build();

        return response;
    }

    @Override
    public ProductResForDetail searchProductById(Long id) {
        Product product = productRepository.findById(id).get();
        ProductResForDetail dto = product.toDTOForDetail();
        return dto;
    }

    @Override
    public PageResponseDTO<ProductResForList> searchProductsByConditionWithPaging(ProductSearchCondition condition, PageRequestDTO pageRequest) {

        Pageable pageable = PageRequest.of(
                pageRequest.getPage()-1,
                pageRequest.getSize(),
                Sort.by("id").descending()
        );

        Page<Product> page = productRepository.searchByConditionWithPaging(condition, pageable);

        List<ProductResForList> dtoList = page.get().map(product -> product.toDTOForList()).toList();

        PageResponseDTO<ProductResForList> response = PageResponseDTO.<ProductResForList>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequest)
                .totalDataCount(page.getTotalElements())
                .build();

        return response;
    }

    @Override
    @Transactional
    public Long modify(Long id, ProductRequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. id: " + id));

        Category category = categoryRepository.findById(dto.getCategory().getId()).get();
        Brand brand = brandRepository.findById(dto.getBrand().getId()).get();
        DeliveryPolicy deliveryPolicy = deliveryPolicyRepository.findById(dto.getDeliveryPolicy().getId()).get();
        DetailInfo detailInfo = detailInfoRepository.findById(dto.getDetailInfo().getId()).get();

        DetailInfo modifiedDetailInfo = detailInfo.changeDetailInfo(dto.getDetailInfo());

        // 옵션 수정
        Map<Long, ProductOptionDTO> existingOptionDtoMap = dto.getOptions().stream().collect(Collectors.toMap(
                optionDto -> optionDto.getId(), optionDto -> optionDto
        ));

        for (ProductOption option : product.getProductOptions()) {
            ProductOptionDTO optionDto = existingOptionDtoMap.get(option.getId());
            if (optionDto.isDeleted()) {
                option.setDeleted(true);
            } else {
                option.setOptionName(optionDto.getOptionName());
                option.setPurchasePrice(optionDto.getPurchasePrice());
                option.setSellingPrice(optionDto.getSellingPrice());
                option.setCurrentStock(optionDto.getCurrentStock());
                option.setInitialStock(optionDto.getInitialStock());
                option.setSafetyStock(optionDto.getSafetyStock());
                option.setImageUrl(optionDto.getImageUrl());
                option.setDisplayOrder(optionDto.getDisplayOrder());
                option.setDeleted(optionDto.isDeleted());
            }
        }

        // 새로 추가된 옵션
        List<ProductOptionDTO> newOptions = dto.getOptions().stream()
                .filter(option -> option.getId() == null).toList();

        for (ProductOptionDTO newOptionDto : newOptions) {
            ProductOption entity = newOptionDto.toEntity();
            product.addProductOption(entity);
        }

        product.setCategory(category);
        product.setBrand(brand);
        product.setBasicInfo(dto.getBasicInfo().toDomain());
        product.setSaleInfo(dto.getSaleInfo().toDomain());
        product.setDeliveryPolicy(deliveryPolicy);
        product.setDetailInfo(modifiedDetailInfo);
        product.setMainImages(dto.getMainImages().stream().map(img -> img.toDomain()).toList());
        product.setDetailImages(dto.getDetailImages().stream().map(img -> img.toDomain()).toList());

        return productRepository.findById(id).get().getId();
    }

    @Override
    public List<Brand> getBrandsByCategory(List<Long> depth3CategoryIds) {
        return productRepository.findBrandListByCategoryIds(depth3CategoryIds);
    }

}
