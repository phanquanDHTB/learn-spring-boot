package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.models.OrderDetail;
import com.learn_spring_boot.learn_spring_boot.services.OrderDetailService;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/orderdetail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable Long id){
        List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(id);
        return ResponseEntity.ok().body(ResponseUtil.ok(orderDetailList, "Successfully"));
    }
}
