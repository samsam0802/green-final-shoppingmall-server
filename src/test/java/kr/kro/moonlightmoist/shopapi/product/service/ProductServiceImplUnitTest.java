//package kr.kro.moonlightmoist.shopapi.product.service;
//
//import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
//import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
//import kr.kro.moonlightmoist.shopapi.product.dto.ProductRequest;
//import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceImplUnitTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @Test
//    @DisplayName("상품 등록 성공")
//    public void register_Success() {
//        ProductRequest request = ProductRequest.builder()
//                .productName("테스트 상품")
//                .productCode("abc")
//                .searchKeywords("헤라, 이니스프리")
//                .exposureStatus(ExposureStatus.EXPOSURE)
//                .saleStatus(SaleStatus.ON_SALE)
//                .description("설명")
//                .cancelable(true)
//                .deleted(false)
//                .build();
//
//        
//    }
//}
