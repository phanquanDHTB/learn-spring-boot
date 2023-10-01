package com.learn_spring_boot.learn_spring_boot.controllers;

import com.learn_spring_boot.learn_spring_boot.dtos.ProductDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.Product;
import com.learn_spring_boot.learn_spring_boot.services.ProductService;
import com.learn_spring_boot.learn_spring_boot.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("")
    public ResponseEntity<?> createProduct (@Valid @RequestBody ProductDTO productDTO , BindingResult result) throws DataNotFoundException {
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
    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByNameProduct(@PathVariable String name) {
        Optional<?> listProduct = Optional.ofNullable(productService.findProductByName(name));
        return ResponseEntity.ok(ResponseUtil.ok(listProduct, "Successfully"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdProduct(@PathVariable Long id){
        try {
            Product product = productService.findProductById(id);
            return ResponseEntity.ok(ResponseUtil.ok(product, "Successfully"));
        } catch(Exception exception) {
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(exception.getMessage()));
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProductByPage(
            @RequestParam int page,
            @RequestParam int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(ResponseUtil.ok(productService.getProductByPage(pageRequest)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(ResponseUtil.badRequest(errorMessages.get(0)));
            }
            Product newProduct = productService.updateProduct(productDTO, id);
            return ResponseEntity.ok(ResponseUtil.ok(newProduct, "Successfully"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(ResponseUtil.badRequest(exception.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(ResponseUtil.message(productService.deleteProduct(id)));
    }
}
