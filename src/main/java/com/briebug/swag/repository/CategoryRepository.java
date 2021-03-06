package com.briebug.swag.repository;

import com.briebug.swag.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByNameIgnoreCase(String name);

}
