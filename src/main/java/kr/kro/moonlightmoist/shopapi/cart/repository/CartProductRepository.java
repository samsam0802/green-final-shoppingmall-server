package kr.kro.moonlightmoist.shopapi.cart.repository;

import kr.kro.moonlightmoist.shopapi.cart.domain.CartProduct;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
//    @Query("select cp from CartProduct cp where cp.id=:id")
//    CartProduct getItemByCartProductId(@Param("id") Long id);


    @Query("select cp.cart.id from CartProduct cp where cp.id = :cartProductId")
    Long findCartIdByCartProductId(@Param("cartProductId") Long cartProductId);


    @Query("""
            select new kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO(
            c.id, po.id, pp.brand.name, pp.basicInfo.productName, po.optionName, po.sellingPrice, c.quantity,
            (select pi.imageUrl from Product p join p.mainImages pi where p.id = pp.id
            and pi.imageType = kr.kro.moonlightmoist.shopapi.product.domain.ImageType.THUMBNAIL 
            and pi.displayOrder = 0)
            )
            from CartProduct c
            join c.cart cc
            join c.productOption po
            join po.product pp
            where cc.id = :cartId
            """)
    List<CartProductListDTO> getItemsByCartId(@Param("cartId") Long cartId);

    @Query("select cp from CartProduct cp where cp.cart.owner.id=:userId and cp.productOption.id=:productOptionId")
    Optional<CartProduct> getItemOfProductOptionIdAndUserId(@Param("userId") Long userId, @Param("productOptionId") Long productOptionId);


    // 특정 유저 id를 가진 유저의 장바구니 상품을 모두 삭제하는 메서드
    // 반환 타입이 int인 이유는 삭제한 장바구니 상품 엔티티의 갯수를 반환하기 때문이다.
    // 데이터를 변경(update 또는 delete 또는 insert 같은)하는 쿼리를 실행할 때 JPA에게 어노테이션으로 알려줘야 한다.
    // 그래서 Modifying 어노테이션을 붙임
    // Transactional 어노테이션을 붙인 이유는 모든 데이터베이스의 상태를 변경하는 작업은 트랜잭션 내에서 실행되어야 하기 때문이다.
    // 이 어노테이션이 없으면 트랜잭션 오류가 발생할 수 있다.
    @Modifying
    @Transactional
    @Query("delete from CartProduct cp where cp.cart.owner.id =:userId")
    int deleteAllByUserId(@Param("userId") Long userId);

}
