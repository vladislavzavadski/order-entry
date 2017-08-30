package com.netcracker.orderentry.inventory.repository;

import com.netcracker.orderentry.inventory.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select count (o) from Order o where o.email = ?1")
    int selectOrdersCountByEmail(String email);

    @Query("select sum(o.totalPrice) from Order o where o.email = ?1")
    double getTotalPriceByEmail(String email);
    List<Order> findOrdersByEmail(String email, Pageable pageable);
    List<Order> findOrderByPaidEquals(boolean paid, Pageable pageable);
}
