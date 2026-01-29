package com.prashant.backendorderservice.service;

import com.prashant.backendorderservice.model.Order;
import com.prashant.backendorderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    public ArrayList<Order> arr= new ArrayList<>();
    public String createOrder(Long CustomerId, String description){

        Order order = new Order();
        order.setCustomerId(CustomerId);
        order.setDescription(description);
//        arr.add(order);
        orderRepository.save(order);
        return "Successfully created Order: "+order.getCustomerId();
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id); //temporary
    }
}
