package com.briebug.swag.repository;

import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Sql({"classpath:scripts/categories.sql", "classpath:scripts/products.sql"})
class ProductRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
        assertNotNull(entityManager);
        assertNotNull(dataSource);
        assertNotNull(productRepository);
    }

    @Test
    void testDatabaseShouldHaveOneProduct() {
        List<Product> products = productRepository.findAll();
        assertEquals(0,products.size(),"Products table should have 0 records");
    }

}