package com.learn_spring_boot.learn_spring_boot.repositories;

import com.learn_spring_boot.learn_spring_boot.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long id);
}
