package kr.kro.moonlightmoist.shopapi.util;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Faq;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import kr.kro.moonlightmoist.shopapi.product.domain.ExposureStatus;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.domain.SaleStatus;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;

import java.time.LocalDate;

public class EntityFactory {

    public static Category createCategory(String name,int depth, int ord) {
        return Category.builder()
                .name(name)
                .depth(depth)
                .displayOrder(ord)
                .deleted(false)
                .build();
    }

    public static Brand createBrand(String name) {
        return Brand.builder()
                .name(name)
                .deleted(false)
                .build();
    }

    public static Product createProduct(Category category, Brand brand) {
        return Product.builder()
                .productName("임시상품")
                .brand(brand)
                .category(category)
                .productCode("임시상품코드")
                .searchKeywords("임시키워드")
                .exposureStatus(ExposureStatus.EXPOSURE)
                .saleStatus(SaleStatus.ON_SALE)
                .description("임시상품설명")
                .cancelable(true)
                .deleted(false)
                .build();
    }

    public static UserGrade createUserGrade () {
        return UserGrade.builder()
                .grade("BRONZE")
                .minTotalPoints(0)
                .freeDeliveryMinAmount(30000)
                .description("브론즈는 혜택이 없습니다.")
                .disCountRate(0)
                .build();
    }


    public static User createUser (UserGrade userGrade) {
        return User.builder()
                .loginId("user")
                .passwordHash("123123")
                .name("유저")
                .email("user@naver.com")
                .phoneNumber("01012345678")
                .postalCode("11111")
                .address("성남")
                .addressDetail("분당")
                .birthDate(LocalDate.of(2025,11,11))
                .emailAgreement(true)
                .smsAgreement(false)
                .userGrade(userGrade)
                .userRole(UserRole.USER)
                .build();
    }

    public static ProductOption createProductOption (String name, Product product) {
        return ProductOption.builder()
                .product(product)
                .optionName(name)
                .purchasePrice(1000)
                .sellingPrice(5000)
                .currentStock(30)
                .initialStock(50)
                .safetyStock(10)
                .imageUrl("url")
                .displayOrder(1)
                .build();
    }

    public static Faq createFaq (User user) {
        return  Faq.builder()
                .user(user)
                .inquiryType(InquiryType.ETC)
                .title("결제가 되지않습니다")
                .answer("재결제를 해보시길 권장드립니다")
                .build();
    }


}
