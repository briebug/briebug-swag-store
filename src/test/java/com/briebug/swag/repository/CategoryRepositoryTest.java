package com.briebug.swag.repository;

import com.briebug.swag.model.Category;
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
@Sql({"classpath:scripts/categories.sql"})
public class CategoryRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
        assertNotNull(entityManager);
        assertNotNull(dataSource);
        assertNotNull(categoryRepository);
    }

    @Test
    void testDatabaseShouldHaveTwoCategories() {
        List<Category> categories = categoryRepository.findAll();
        assertEquals(2,categories.size(),"Category table should have 2 records");
        assertEquals("Apparel", categories.get(0).getName(), "Category 1 should be 'Apparel'");
        assertEquals("Mugs", categories.get(1).getName(), "Category 2 should be 'Mugs'");
    }

}
