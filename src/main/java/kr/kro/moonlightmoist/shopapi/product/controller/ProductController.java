package kr.kro.moonlightmoist.shopapi.product.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.product.dto.*;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final S3UploadService s3UploadService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("")
    public ResponseEntity<String> productRegister(
            @RequestPart("product") ProductRequest product,
            @RequestPart(value = "optionImages", required = false) List<MultipartFile> optionImages,
            @RequestPart("mainImages") List<MultipartFile> mainImages,
            @RequestPart(value = "detailImages", required = false) List<MultipartFile> detailImages
            ) {
        if (optionImages == null) {
            optionImages = new ArrayList<>();
        }
        if (detailImages == null) {
            detailImages = new ArrayList<>();
        }

        System.out.println("product = " + product);

        Long id = productService.register(product);

        System.out.println("optionImages = "+ optionImages);
        System.out.println("mainImages = " + mainImages);
        System.out.println("detailImages = " + detailImages);

        System.out.println("id = " + id);

        ProductImagesUrlDTO urlDTO = ProductImagesUrlDTO.builder()
                .mainImageUrls(new ArrayList<>())
                .optionImageUrls(new ArrayList<>())
                .detailImageUrls(new ArrayList<>())
                .build();

        for(int i=0; i<optionImages.size(); i++) {
            String url = s3UploadService.uploadOneFile(optionImages.get(i), "products/" + id + "/");
            urlDTO.getOptionImageUrls().add(url);
        }

        for(int i=0; i<mainImages.size(); i++) {
            String url = s3UploadService.uploadOneFile(mainImages.get(i), "products/" + id + "/");
            urlDTO.getMainImageUrls().add(url);
        }

        for(int i=0; i<detailImages.size(); i++) {
            String url = s3UploadService.uploadOneFile(detailImages.get(i), "products/" + id + "/");
            urlDTO.getDetailImageUrls().add(url);
        }

        productService.addImageUrls(id, urlDTO);

        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyProduct(
            @PathVariable(name = "id") Long id,
            @RequestPart(name = "product") ProductRequest product,
            MultipartHttpServletRequest request
            ) {
        System.out.println("id = " + id);
        System.out.println("product = " + product);
        System.out.println("mainImages = " + product.getMainImages());
        Map<String, MultipartFile> fileMap = request.getFileMap();
        System.out.println("fileMap = " + fileMap);

        for(ProductMainImageDto img : product.getMainImages()) {
            if (img.getType().equals("new")) {
                String key = "mainImageFile-"+img.getDisplayOrder();
                MultipartFile multipartFile = fileMap.get(key);
                String url = s3UploadService.uploadOneFile(multipartFile, "products/" + id + "/");
                img.setImageUrl(url);
            }
        }

        product.getDetailImages().stream()
                .filter(img -> img.getType().equals("new"))
                        .forEach(img -> {
                            String key = "detailImageFile-" + img.getDisplayOrder();
                            MultipartFile multipartFile = fileMap.get(key);
                            String url = s3UploadService.uploadOneFile(multipartFile, "products/" + id + "/");
                            img.setImageUrl(url);
                        });

        product.getOptions().stream()
                .filter(option -> option.getType().equals("new"))
                        .forEach(option -> {
                            String key = "optionImageFile-" + option.getDisplayOrder();
                            MultipartFile multipartFile = fileMap.get(key);
                            String url = s3UploadService.uploadOneFile(multipartFile, "products/" + id + "/");
                            option.setImageUrl(url);
                        });

        System.out.println("product = " + product);

        productService.modify(id, product);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("")
    public ResponseEntity<PageResponseDTO<ProductResForList>> getProductsByCategory(
            @RequestParam("categoryId") List<Long> depth3CategoryIds,
            @RequestParam("brandId") Long brandId,
            @ModelAttribute PageRequestDTO pageRequest
            ) {

        System.out.println("brandId = " + brandId);

        PageResponseDTO<ProductResForList> pageResponse = productService.searchProductsByCategory(depth3CategoryIds, brandId, pageRequest);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getBrandsByCategory(
            @RequestParam("categoryId") List<Long> depth3CategoryIds
    ) {
        List<Brand> brands = productService.getBrandsByCategory(depth3CategoryIds);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResForDetail> getProductById(@PathVariable(name = "id") Long id) {

        ProductResForDetail res = productService.searchProductById(id);

        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<PageResponseDTO<ProductResForList>> searchProductsByCondition(
            @RequestBody ProductSearchCondition condition,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        PageRequestDTO pageRequest = PageRequestDTO.builder().page(page).size(size).build();
        System.out.println("condition = " + condition);
        System.out.println("pageRequest = " + pageRequest);
        System.out.println("page = " + page);
        System.out.println("size = " + size);


        PageResponseDTO<ProductResForList> result = productService.searchProductsByConditionWithPaging(condition, pageRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/new")
    public ResponseEntity<List<ProductResForList>> getNewProducts() {
        List<ProductResForList> result = productService.getNewProducts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/best")
    public ResponseEntity<List<ProductResForList>> getBestProducts() {
        List<ProductResForList> result = productService.getBestProducts();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("ok");
    }
}
