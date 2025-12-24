package kr.kro.moonlightmoist.shopapi.order.repository;

import kr.kro.moonlightmoist.shopapi.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("SELECT op.orderProductStatus, COUNT(op) FROM OrderProduct op " +
            "WHERE op.order.user.id=:userId AND op.order.createdAt BETWEEN :start AND :end " +
            "AND op.deleted=false " +
            "GROUP BY op.orderProductStatus")
    List<Object[]> countStatusByUserIdAndPeriod(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
