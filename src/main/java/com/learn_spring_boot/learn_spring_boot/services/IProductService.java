package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.ProductDTO;
import com.learn_spring_boot.learn_spring_boot.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Page<Product> getProductByPage(PageRequest pageRequest);
    Product getProductById(Long id) throws Exception;
    Product updateProduct(ProductDTO productDTO, Long id);
    String deleteProduct(Long id);
    List<Product> findProductByName(String name);
    Product findProductById(Long id) throws Exception;
}
