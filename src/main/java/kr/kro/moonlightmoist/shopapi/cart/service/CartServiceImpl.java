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
//        Cart cart = cartRepository.findByOwner(user).orElseThrow();
        Optional<Cart> cartOptional = cartRepository.findByOwner(user);

        // 만약 장바구니가 생성되지 않았으면 먼저 장바구니부터 생성함
        if(cartOptional.isEmpty()) {
            Cart cart = Cart.builder()
                    .owner(user)
                    .build();
            cartRepository.save(cart);
            cartOptional = cartRepository.findByOwner(user);
        }

        Cart cart = cartOptional.get();

        // 장바구니에서 수량 변경할 때는 id가 null이 아니다.
        if(id != null) {
            CartProduct cartProduct = cartProductRepository.findById(id).orElseThrow();
            cartProduct.changeQty(quantity);
            cartProductRepository.save(cartProduct);
        }



        if(id == null) {
            ProductOption productOption = productOptionRepository.findById(productOptionId).orElseThrow();
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
            else { //기존에 장바구니에 있던 상품이라면 상품 상세 페이지에서 장바구니에 담은 수량만큼 +한다.
                CartProduct cartProduct = foundCartItem.get();
                cartProduct.changeQty(cartProduct.getQuantity() + quantity);
                cartProductRepository.save(cartProduct);
            }
        }

        //유저의 장바구니 id로 장바구니 상품 리스트를 반환
        return cartProductRepository.getItemsByCartId(cart.getId());
    }

    @Override
    public List<CartProductListDTO> remove(Long cartProductId) {
        //cartId 조회
        Long cartId = cartProductRepository.findCartIdByCartProductId(cartProductId);

        //장바구니 상품 삭제
        cartProductRepository.deleteById(cartProductId);
        return cartProductRepository.getItemsByCartId(cartId);
    }

    @Override
    public List<CartProductListDTO> getCartItems(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Optional<Cart> cartOptional = cartRepository.findByOwner(user);
        if(cartOptional.isEmpty()){
            System.out.println("id " + userId + "번 회원의 장바구니가 없습니다.");
            Cart cart = Cart.builder()
                    .owner(user)
                    .build();
            cartRepository.save(cart);
            return cartProductRepository.getItemsByCartId(cart.getId());
        }
        else {
            Cart cart = cartOptional.get();
            return cartProductRepository.getItemsByCartId(cart.getId());
        }
    }

    @Override
    public int removeAllCartItemsOfUser(Long userId) {
        log.info("CartServiceImpl removeAllCartItemsOfUser 메서드 실행 => userId={}",userId);
        return cartProductRepository.deleteAllByUserId(userId);
    }


}
