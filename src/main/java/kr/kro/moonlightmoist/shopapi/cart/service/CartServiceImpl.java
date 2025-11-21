package kr.kro.moonlightmoist.shopapi.cart.service;

import kr.kro.moonlightmoist.shopapi.cart.domain.Cart;
import kr.kro.moonlightmoist.shopapi.cart.domain.CartProduct;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductDTO;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO;
import kr.kro.moonlightmoist.shopapi.cart.repository.CartProductRepository;
import kr.kro.moonlightmoist.shopapi.cart.repository.CartRepository;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import kr.kro.moonlightmoist.shopapi.product.repository.ProductOptionRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UserRepository userRepository;

    @Override
    public List<CartProductListDTO> addOrModify(CartProductDTO dto) {
        Long id = dto.getId();
        Long userId = dto.getUserId();
        Long productOptionId = dto.getProductOptionId();
        int quantity = dto.getQuantity();

        //CartProductDTO에 저장된 userId로 cart 조회
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = cartRepository.findByOwner(user);

        // 기존에 장바구니에 있던 상품은 수량만 변경
        if(id != null) {
            CartProduct cartProduct = cartProductRepository.findById(id).orElseThrow();
            cartProduct.changeQty(quantity);
            cartProductRepository.save(cartProduct);
        }



        if(id == null) {
            ProductOption productOption = productOptionRepository.findById(productOptionId).get();
            Optional<CartProduct> foundCartItem = cartProductRepository.getItemOfProductOptionIdAndUserId(userId, productOptionId);
            // 아직 장바구니 상품이 아닌 상품은 장바구니 상품으로 등록
            if(foundCartItem.isEmpty()) {
                CartProduct cartProduct = CartProduct.builder()
                        .cart(cart)
                        .productOption(productOption)
                        .quantity(quantity)
                        .build();
                cartProductRepository.save(cartProduct);
            }
            else {
                CartProduct cartProduct = foundCartItem.get();
                cartProduct.changeQty(cartProduct.getQuantity() + quantity);
                cartProductRepository.save(cartProduct);
            }
        }


        return cartProductRepository.getCartProductsByCartId(cart.getId());
    }

    @Override
    public List<CartProductListDTO> remove(Long cartProductId) {
        //cartId 조회
        Long cartId = cartProductRepository.findCartIdByCartProductId(cartProductId);

        //장바구니 상품 삭제
        cartProductRepository.deleteById(cartProductId);
        return cartProductRepository.getCartProductsByCartId(cartId);
    }
}
