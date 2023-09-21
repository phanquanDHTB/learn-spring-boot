package com.learn_spring_boot.learn_spring_boot.repositories;

import com.learn_spring_boot.learn_spring_boot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
