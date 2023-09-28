package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.OrderDetailDTO;
import com.learn_spring_boot.learn_spring_boot.models.OrderDetail;
import com.learn_spring_boot.learn_spring_boot.repositories.OrderDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    private final OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        return null;
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws Exception {
        return null;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    @Transactional
    public List<OrderDetail> findByOrderId(Long orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        return orderDetailList;
    }
}
