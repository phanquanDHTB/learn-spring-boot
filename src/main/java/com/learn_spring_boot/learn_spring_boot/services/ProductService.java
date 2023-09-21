package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.ProductDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.DataNotFoundException;
import com.learn_spring_boot.learn_spring_boot.models.Category;
import com.learn_spring_boot.learn_spring_boot.models.Product;
import com.learn_spring_boot.learn_spring_boot.repositories.CategoryRepository;
import com.learn_spring_boot.learn_spring_boot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find category with id: " + productDTO.getCategoryId())
        );

        Product newProduct = Product
                .builder()
                .category(category)
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .thumbnail(productDTO.getThumbnail())
                .name(productDTO.getName())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Page<Product> getProductByPage(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product updateProduct(ProductDTO productDTO, Long id) {
        return null;
    }

    @Override
    public String deleteProduct(Long id) {
        return null;
    }

    @Override
    public List<Product> findProductByName(String name) {
        return null;
    }
}
