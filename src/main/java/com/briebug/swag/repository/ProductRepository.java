package com.briebug.swag.repository;

import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategory(Category category);

}
