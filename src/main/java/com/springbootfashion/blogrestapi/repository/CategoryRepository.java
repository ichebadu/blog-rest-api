package com.springbootfashion.blogrestapi.repository;
import com.springbootfashion.blogrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
