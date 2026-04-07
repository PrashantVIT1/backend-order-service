package com.prashant.backendorderservice.orders.repository;


import com.prashant.backendorderservice.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>
{
    List<Order> findAllByUserId(Long id);

    Order findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long orderId);

}
