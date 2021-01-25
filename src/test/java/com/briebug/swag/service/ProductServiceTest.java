package com.briebug.swag.service;

import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;
import com.briebug.swag.model.apparel.Color;
import com.briebug.swag.model.apparel.Shirt;
import com.briebug.swag.model.apparel.Size;
import com.briebug.swag.repository.CategoryRepository;
import com.briebug.swag.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void testListAllProducts() {
        Category apparel = new Category("Apparel");
        Product mockProduct = new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Product mockProduct2 = new Shirt("Briebug Logo Polo","Briebug Logo Polo: Black/LG", apparel, new BigDecimal(20),10, Size.LARGE, Color.BLACK);
        Mockito.doReturn(Arrays.asList(mockProduct,mockProduct2)).when(productRepository).findAll();

        List<Product> products = productService.list();

        Assertions.assertEquals(2,products.size(),"List should return 2 products");
    }

    @Test
    void listByCategory() {
        Category apparel = new Category("Apparel");
        Category mugs = new Category("Coffee Mugs");
        Product mockProduct = new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Product mockProduct2 = new Product();
        mockProduct2.setCategory(mugs);
        Mockito.doReturn(apparel).when(categoryRepository).findByNameIgnoreCase("apparel");
        Mockito.doReturn(Arrays.asList(mockProduct)).when(productRepository).findByCategory(apparel);

        List<Product> products = productService.listByCategory("apparel");

        Assertions.assertEquals(1,products.size(),"List should return 2 products");
    }

    @Test
    void testFindByIdFound() {
        Category apparel = new Category("Apparel");
        Product mockProduct = new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Mockito.doReturn(Optional.of(mockProduct)).when(productRepository).findById(1L);

        Optional<Product> product = productService.get(1);

        Assertions.assertTrue(product.isPresent(),"Product was not found.");
        Assertions.assertSame(product.get(),mockProduct,"Products should be the same");
    }

    @Test
    void testFindByIdNotFound() {
        Category apparel = new Category("Apparel");
        Product mockProduct = new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Mockito.doReturn(Optional.empty()).when(productRepository).findById(1L);

        Optional<Product> product = productService.get(1);

        Assertions.assertFalse(product.isPresent(),"Product was found when it shouldn't have been.");
    }

}