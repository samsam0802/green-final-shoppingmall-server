package kr.kro.moonlightmoist.shopapi.product.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.product.dto.*;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final S3UploadService s3UploadService;

    @PostMapping("")
    public ResponseEntity<String> productRegister(
            @RequestPart("product") ProductRequest product,
            @RequestPart("optionImages") List<MultipartFile> optionImages,
            @RequestPart("mainImages") List<MultipartFile> mainImages,
            @RequestPart("detailImages") List<MultipartFile> detailImages
            ) {

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


//        Long id = productService.register(request);
//        System.out.println("id = " + id);

        return ResponseEntity.ok("ok");
    }

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
    public ResponseEntity<List<ProductResForList>> getProductsByCategory(
            @RequestParam("categoryId") List<Long> depth3CategoryIds) {
        List<ProductResForList> productResList = productService.searchProductsByCategory(depth3CategoryIds);
        return ResponseEntity.ok(productResList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResForDetail> getProductById(@PathVariable(name = "id") Long id) {

        ProductResForDetail res = productService.searchProductById(id);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductResForList>> searchProductsByCondition(
            @RequestBody ProductSearchCondition condition
            ) {
        System.out.println("condition = " + condition);

        List<ProductResForList> products = productService.searchProductsByCondition(condition);

        return ResponseEntity.ok(products);
    }
}
