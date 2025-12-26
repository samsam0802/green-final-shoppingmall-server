package kr.kro.moonlightmoist.shopapi.order.repository;

import kr.kro.moonlightmoist.shopapi.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long>, OrderCustomRepository {
//    Page<Order> findByUserId(Long userId, Pageable pageable);

    @Query("select o from Order o where o.orderNumber=:merchantUid and o.deleted=false")
    Optional<Order> findByMerchantUid(@Param("merchantUid") String merchantUid);
}
