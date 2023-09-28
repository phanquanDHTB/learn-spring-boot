package com.learn_spring_boot.learn_spring_boot.repositories;

import com.learn_spring_boot.learn_spring_boot.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
