package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.configurations.ModelMapperConfig;
import com.learn_spring_boot.learn_spring_boot.dtos.OrderDTO;
import com.learn_spring_boot.learn_spring_boot.dtos.OrderItemDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.*;
import com.learn_spring_boot.learn_spring_boot.repositories.OrderDetailRepository;
import com.learn_spring_boot.learn_spring_boot.repositories.OrderRepository;
import com.learn_spring_boot.learn_spring_boot.repositories.ProductRepository;
import com.learn_spring_boot.learn_spring_boot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Can't find user"));
        //convert orderDTO => Order
        //dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);//đoạn này nên set sẵn trong sql
        order.setTotalMoney(orderDTO.getTotalMoney());
        orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getListItem()) {
            // Tạo một đối tượng OrderDetail từ CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lấy thông tin sản phẩm từ cartItemDTO
            Long productId = orderItemDTO.getProductId();
            int quantity = orderItemDTO.getNumberOfProduct();

            // Tìm thông tin sản phẩm từ cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + productId));

            // Đặt thông tin cho OrderDetail
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProduct(quantity);
            orderDetail.setTotalMoney(product.getPrice() * quantity);
            // Các trường khác của OrderDetail nếu cần
            orderDetail.setPrice(product.getPrice());
            // Thêm OrderDetail vào danh sách
            orderDetails.add(orderDetail);
        }


        // Lưu danh sách OrderDetail vào cơ sở dữ liệu
        orderDetailRepository.saveAll(orderDetails);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        Order selectedOrder = orderRepository.findById(id).orElse(null);
        return selectedOrder;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        return null;
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        //no hard-delete, => please soft-delete
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
