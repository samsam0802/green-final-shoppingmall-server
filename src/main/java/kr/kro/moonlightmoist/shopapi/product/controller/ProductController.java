package kr.kro.moonlightmoist.shopapi.product.controller;

import kr.kro.moonlightmoist.shopapi.aws.service.S3UploadService;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductImagesUrlDTO;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForDetail;
import kr.kro.moonlightmoist.shopapi.product.dto.ProductResForList;
import kr.kro.moonlightmoist.shopapi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
            @RequestPart("mainImages") List<MultipartFile> mainImages
            ) {

        System.out.println("product = " + product);

        Long id = productService.register(product);

        System.out.println("optionImages = "+ optionImages);
        System.out.println("mainImages = " + mainImages);

        System.out.println("id = " + id);

        ProductImagesUrlDTO urlDTO = ProductImagesUrlDTO.builder()
                .mainImageUrls(new ArrayList<>())
                .optionImageUrls(new ArrayList<>())
                .build();

        for(int i=0; i<optionImages.size(); i++) {
            String url = s3UploadService.uploadOneFile(optionImages.get(i), "products/" + id + "/");
            urlDTO.getOptionImageUrls().add(url);
        }

        for(int i=0; i<mainImages.size(); i++) {
            String url = s3UploadService.uploadOneFile(mainImages.get(i), "products/" + id + "/");
            urlDTO.getMainImageUrls().add(url);
        }

        productService.addImageUrls(id, urlDTO);


//        Long id = productService.register(request);
//        System.out.println("id = " + id);

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

}
