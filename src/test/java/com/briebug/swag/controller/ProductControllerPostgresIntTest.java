package com.briebug.swag.controller;

import com.briebug.swag.model.Category;
import com.briebug.swag.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest
public class ProductControllerPostgresIntTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("bb-swag");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);
    }

    @Test
    void contextLoads() {
        System.out.println("Context Loads!");
        Assertions.assertNotNull(categoryRepository);
    }

    @Test
    void testFindAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        Assertions.assertEquals(1,categories.size());
    }

}
