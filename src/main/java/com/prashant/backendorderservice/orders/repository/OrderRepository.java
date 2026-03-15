package com.prashant.backendorderservice.orders.repository;

import com.prashant.backendorderservice.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>
{


}
