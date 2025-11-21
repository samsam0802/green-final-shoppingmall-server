package kr.kro.moonlightmoist.shopapi.cart.repository;

import kr.kro.moonlightmoist.shopapi.cart.domain.CartProduct;
import kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO;
import kr.kro.moonlightmoist.shopapi.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
//    @Query("select cp from CartProduct cp where cp.id=:id")
//    CartProduct getItemByCartProductId(@Param("id") Long id);


    @Query("select cp.cart.id from CartProduct cp where cp.id = :cartProductId")
    Long findCartIdByCartProductId(@Param("cartProductId") Long cartProductId);


    @Query("""
            select new kr.kro.moonlightmoist.shopapi.cart.dto.CartProductListDTO(
            c.id, po.id, pp.brand.name, pp.productName, po.optionName, po.sellingPrice, c.quantity,
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
    List<CartProductListDTO> getCartProductsByCartId(@Param("cartId") Long cartId);

    @Query("select cp from CartProduct cp where cp.cart.owner.id=:userId and cp.productOption.id=:productOptionId")
    Optional<CartProduct> getItemOfProductOptionIdAndUserId(@Param("userId") Long userId, @Param("productOptionId") Long productOptionId);


}
