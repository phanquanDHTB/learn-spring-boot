package com.learn_spring_boot.learn_spring_boot.repositories;

import com.learn_spring_boot.learn_spring_boot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByName(String name);
}
