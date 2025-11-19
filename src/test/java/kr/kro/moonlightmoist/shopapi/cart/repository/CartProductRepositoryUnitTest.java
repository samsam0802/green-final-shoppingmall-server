package kr.kro.moonlightmoist.shopapi.cart.repository;

import jakarta.persistence.EntityManager;
import kr.kro.moonlightmoist.shopapi.brand.domain.Brand;
import kr.kro.moonlightmoist.shopapi.brand.repository.BrandRepository;
import kr.kro.moonlightmoist.shopapi.cart.domain.Cart;
import kr.kro.moonlightmoist.shopapi.cart.domain.CartProduct;
import kr.kro.moonlightmoist.shopapi.category.domain.Category;
import kr.kro.moonlightmoist.shopapi.category.repository.CategoryRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.Product;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.repository.UserGradeRepository;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class CartProductRepositoryUnitTest {

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserGradeRepository userGradeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Test
    @DisplayName("장바구니 상품 등록 테스트")
    public void registerCartProductTest() {
        UserGrade userGrade = EntityFactory.createUserGrade();
        UserGrade savedUserGrade = userGradeRepository.save(userGrade);

        User user = EntityFactory.createUser(savedUserGrade);
        User savedUser = userRepository.save(user);

        Category category = EntityFactory.createCategory("메이크업", 0, 0);
        Category savedCategory = categoryRepository.save(category);

        Brand brand = EntityFactory.createBrand("이니스프리");
        Brand savedBrand = brandRepository.save(brand);

        Product product = EntityFactory.createProduct(savedCategory, savedBrand);
        Product savedProduct = productRepository.save(product);

        ProductOption productOption = EntityFactory.createProductOption("옵션1", savedProduct);
        ProductOption savedProductOption = productOptionRepository.save(productOption);

        Cart cart = EntityFactory.createCart(savedUser);
        Cart savedCart = cartRepository.save(cart);

        CartProduct cartProduct = CartProduct.builder()
                .cart(savedCart)
                .productOption(savedProductOption)
                .quantity(2)
                .build();
        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);

        assertThat(savedCartProduct.getId()).isNotNull();
        assertThat(savedCartProduct.getCart()).isNotNull();
        assertThat(savedCartProduct.getProductOption()).isNotNull();
        assertThat(savedCartProduct.getQuantity()).isEqualTo(2);
        assertThat(savedCartProduct.getCreatedAt()).isNotNull();
        assertThat(savedCartProduct.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 조회 테스트")
    public void checkCartProductTest() {
        UserGrade userGrade = EntityFactory.createUserGrade();
        UserGrade savedUserGrade = userGradeRepository.save(userGrade);

        User user = EntityFactory.createUser(savedUserGrade);
        User savedUser = userRepository.save(user);


        Category category = EntityFactory.createCategory("메이크업", 0, 0);
        Category savedCategory = categoryRepository.save(category);

        Brand brand = EntityFactory.createBrand("이니스프리");
        Brand savedBrand = brandRepository.save(brand);

        Product product = EntityFactory.createProduct(savedCategory, savedBrand);
        Product savedProduct = productRepository.save(product);

        ProductOption productOption = EntityFactory.createProductOption("옵션2", savedProduct);
        ProductOption savedProductOption = productOptionRepository.save(productOption);

        Cart cart = EntityFactory.createCart(savedUser);
        Cart savedCart = cartRepository.save(cart);

        CartProduct cartProduct = CartProduct.builder()
                .cart(savedCart)
                .productOption(savedProductOption)
                .quantity(2)
                .build();
        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);

        CartProduct findCartProduct = cartProductRepository.findById(savedCartProduct.getId()).get();

        assertThat(findCartProduct.getId()).isNotNull();
        assertThat(findCartProduct.getCart()).isNotNull();
        assertThat(findCartProduct.getProductOption()).isNotNull();
        assertThat(findCartProduct.getQuantity()).isEqualTo(2);
        assertThat(findCartProduct.getCreatedAt()).isNotNull();
        assertThat(findCartProduct.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경 테스트")
    public void changeQuantityTest() {
        UserGrade userGrade = EntityFactory.createUserGrade();
        UserGrade savedUserGrade = userGradeRepository.save(userGrade);

        User user = EntityFactory.createUser(savedUserGrade);
        User savedUser = userRepository.save(user);


        Category category = EntityFactory.createCategory("메이크업", 0, 0);
        Category savedCategory = categoryRepository.save(category);

        Brand brand = EntityFactory.createBrand("이니스프리");
        Brand savedBrand = brandRepository.save(brand);

        Product product = EntityFactory.createProduct(savedCategory, savedBrand);
        Product savedProduct = productRepository.save(product);

        ProductOption productOption = EntityFactory.createProductOption("옵션1", savedProduct);
        ProductOption savedProductOption = productOptionRepository.save(productOption);

        Cart cart = EntityFactory.createCart(savedUser);
        Cart savedCart = cartRepository.save(cart);

        CartProduct cartProduct = CartProduct.builder()
                .cart(savedCart)
                .productOption(savedProductOption)
                .quantity(2)
                .build();
        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);

        CartProduct findCartProduct = cartProductRepository.findById(savedCartProduct.getId()).get();

        findCartProduct.changeQty(3);
        CartProduct resultCartProduct = cartProductRepository.save(findCartProduct);

        assertThat(resultCartProduct.getId()).isNotNull();
        assertThat(resultCartProduct.getCart()).isNotNull();
        assertThat(resultCartProduct.getProductOption()).isNotNull();
        assertThat(resultCartProduct.getQuantity()).isEqualTo(3);
        assertThat(resultCartProduct.getCreatedAt()).isNotNull();
        assertThat(resultCartProduct.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 삭제 테스트")
    public void deleteCartProduct() {
        UserGrade userGrade = EntityFactory.createUserGrade();
        UserGrade savedUserGrade = userGradeRepository.save(userGrade);

        User user = EntityFactory.createUser(savedUserGrade);
        User savedUser = userRepository.save(user);


        Category category = EntityFactory.createCategory("메이크업", 0, 0);
        Category savedCategory = categoryRepository.save(category);

        Brand brand = EntityFactory.createBrand("이니스프리");
        Brand savedBrand = brandRepository.save(brand);

        Product product = EntityFactory.createProduct(savedCategory, savedBrand);
        Product savedProduct = productRepository.save(product);

        ProductOption productOption = EntityFactory.createProductOption("옵션1", savedProduct);
        ProductOption savedProductOption = productOptionRepository.save(productOption);

        Cart cart = EntityFactory.createCart(savedUser);
        Cart savedCart = cartRepository.save(cart);

        CartProduct cartProduct = CartProduct.builder()
                .cart(savedCart)
                .productOption(savedProductOption)
                .quantity(2)
                .build();
        CartProduct savedCartProduct = cartProductRepository.save(cartProduct);

        cartProductRepository.deleteById(savedCartProduct.getId());

        assertThat(cartProductRepository.findById(savedCartProduct.getId())).isEmpty();
    }
}
