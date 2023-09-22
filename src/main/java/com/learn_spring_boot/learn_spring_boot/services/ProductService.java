package com.learn_spring_boot.learn_spring_boot.services;

import com.learn_spring_boot.learn_spring_boot.dtos.ProductDTO;
import com.learn_spring_boot.learn_spring_boot.exceptions.BadRequestException;
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
import java.util.Optional;

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
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductDTO productDTO, Long id) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() ->
                new BadRequestException("Cannot find category with id: " + productDTO.getCategoryId())
        );
        Product existingProduct = productRepository.findById(id).get();
        existingProduct.setCategory(category);
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setThumbnail(productDTO.getThumbnail());
        existingProduct.setDescription(productDTO.getDescription());
        return productRepository.save(existingProduct);
    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.deleteById(id);
        return "Delete successfully";
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Product findProductById(Long id) throws Exception {
        return productRepository.findById(id).get();
    }
}
