package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.dtos.OrderDTO;
import com.learn_spring_boot.learn_spring_boot.models.Order;
import com.learn_spring_boot.learn_spring_boot.services.OrderService;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
            }
            Order newOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok().body(ResponseUtil.ok(newOrder, "Successfully"));
        } catch(Exception exception) {
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(exception.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok().body(ResponseUtil.ok(order, "Successfully"));
    }
    @GetMapping("/findByUser/{id}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable Long id){
        List<Order> orders = orderService.findByUserId(id);
        return ResponseEntity.ok().body(ResponseUtil.ok(orders, "Successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body(ResponseUtil.message("Successfully"));
    }
}
