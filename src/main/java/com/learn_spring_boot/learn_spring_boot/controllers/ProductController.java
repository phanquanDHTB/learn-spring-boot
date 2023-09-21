package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.dtos.ProductDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.Product;
import com.learn_spring_boot.learn_spring_boot.services.ProductService;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("")
    public ResponseEntity<?> createProduct (@Valid @RequestBody ProductDTO productDTO, BindingResult result) throws DataNotFoundException {
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(ResponseUtil.ok(newProduct, "Successfully"));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(e.getMessage()));
        }
    }
}
