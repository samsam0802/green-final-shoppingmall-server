//package kr.kro.moonlightmoist.shopapi.Makeup.service;
//
//import kr.kro.moonlightmoist.shopapi.Makeup.dto.MakeupProductDTO;
//import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
//import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
//import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class MakeupImportService {
//    private final BrandRepository brandRepository;
//    private final CategoryRepository categoryRepository;
//    private final ProductRepository productRepository;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final String apiUrl = "https://makeup-api.herokuapp.com/api/v1/products.json";
//
//    public MakeupImportService(BrandRepository brandRepo,
//                               CategoryRepository categoryRepo,
//                               ProductRepository productRepo) {
//        this.brandRepository = brandRepo;
//        this.categoryRepository = categoryRepo;
//        this.productRepository = productRepo;
//    }
//
//    public void importProducts() {
//        MakeupProductDTO[] products = restTemplate.getForObject(apiUrl, MakeupProductDTO[].class);
//
//        for(MakeupProductDTO dto : products) {
//            if(brandRepository.findByName(dto.getBrand()).isPresent()) {
//                continue;
//            }
//            brandRepository.save(dto.getBrand());
//        }
//    }
//
//}
