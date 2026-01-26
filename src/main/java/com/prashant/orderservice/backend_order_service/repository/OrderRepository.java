package com.prashant.orderservice.backend_order_service.repository;

import com.prashant.orderservice.backend_order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>
{

}
