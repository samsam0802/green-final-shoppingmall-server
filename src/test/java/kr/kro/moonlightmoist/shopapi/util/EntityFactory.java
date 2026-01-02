package kr.kro.moonlightmoist.shopapi.util;

import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.cart.domain.Cart;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Faq;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import kr.kro.moonlightmoist.shopapi.order.domain.OrderProductStatus;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicy;
import kr.kro.moonlightmoist.shopapi.policy.deliveryPolicy.domain.DeliveryPolicyType;
import kr.kro.moonlightmoist.shopapi.product.domain.*;
import kr.kro.moonlightmoist.shopapi.review.domain.Review;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewComment;
import kr.kro.moonlightmoist.shopapi.review.domain.ReviewLike;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;

import java.time.LocalDate;
import java.util.List;

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
                .basicInfo(BasicInfo.builder()
                        .productName("임시상품")
                        .productCode("임시상품코드")
                        .searchKeywords("임시키워드")
                        .description("임시상품설명")
                        .build())
                .saleInfo(SaleInfo.builder()
                        .exposureStatus(ExposureStatus.EXPOSURE)
                        .saleStatus(SaleStatus.ON_SALE)
                        .cancelable(true)
                        .build())
                .brand(brand)
                .category(category)
                .deleted(false)
                .build();
    }


    public static User createUser () {
        return User.builder()
                .loginId("user")
                .password("123123")
                .name("유저")
                .email("user@naver.com")
                .phoneNumber("01012345678")
                .postalCode("11111")
                .address("성남")
                .addressDetail("분당")
                .birthDate(LocalDate.of(2025,11,11))
                .emailAgreement(true)
                .smsAgreement(false)
                .userGrade(UserGrade.BRONZE)
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
    public static Cart createCart(User user) {
        return Cart.builder()
                .owner(user)
                .build();
    }


    public static DeliveryPolicy createDeliveryPolicy() {
        return DeliveryPolicy.builder()
                .name("이름")
                .policyType(DeliveryPolicyType.CONDITIONAL_FREE)
                .basicDeliveryFee(3000)
                .freeConditionAmount(50000)
                .defaultPolicy(true)
                .deleted(false)
                .build();
    }

    public static Order createOrder(User user) {
        return Order.builder()
                .user(user)
                .orderNumber("주문번호")
                .paymentMethod("CARD")
                .deliveryFee(3000)
                .expectedDeliveryDate(LocalDate.of(2025,01,01))
                .totalProductAmount(30000)
                .discountAmount(3000)
                .usedPoints(3000)
                .finalAmount(30000)
                .receiverName("이름")
                .receiverPhone("01012345678")
                .postalCode("123456")
                .streetAddress("도로명주소")
                .detailedAddress("상세주소")
                .deliveryRequest("배송요청사항")
                .deleted(false)
                .build();
    }

    public static ReviewComment createReviewComment(User user, Review review){
        return ReviewComment.builder()
                .user(user)
                .review(review)
                .visible(true)
                .deleted(false)
                .content("리뷰댓글")
                .build();
    }

    public static ReviewLike createReviewLike(User user, Review review){
        return ReviewLike.builder()
                .user(user)
                .review(review)
                .deleted(false)
                .build();
    }

    public static OrderProduct createOrderProduct(Order order, ProductOption productOption){
        return OrderProduct.builder()
                .order(order)
                .productOption(productOption)
                .quantity(5)
                .purchasedPrice(1000)
                .orderProductStatus(OrderProductStatus.PAID)
                .deleted(false)
                .build();
    }

    public static Review createReview(User user, Product product){
        return Review.builder()
                .user(user)
                .content("리뷰내용1")
                .rating(5)
                .visible(true)
                .deleted(false)
                .build();
    }

}
