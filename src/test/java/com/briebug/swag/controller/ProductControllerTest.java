package com.briebug.swag.controller;

import com.briebug.swag.exception.ResourceNotFoundException;
import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;
import com.briebug.swag.model.apparel.Color;
import com.briebug.swag.model.apparel.Shirt;
import com.briebug.swag.model.apparel.Size;
import com.briebug.swag.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listAllProducts() throws Exception {
        Category apparel = new Category("Apparel");
        Product polo = new Shirt(1L,"Briebug Logo Polo","Briebug Logo Polo: Black/LG", apparel, new BigDecimal(20),10, Size.LARGE, Color.BLACK);
        List<Product> products = new ArrayList<>();
        products.add(polo);
        Mockito.doReturn(products).when(productService).list();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));

        Mockito.verify(productService, times(1)).list();
    }

    @Test
    void listProductsByCategory() throws Exception {
        Category apparel = new Category("Apparel");
        Category mugs = new Category("Coffee Mugs");
        Product polo = new Shirt(1L,"Briebug Logo Polo","Briebug Logo Polo: Black/LG", apparel, new BigDecimal(20),10, Size.LARGE, Color.BLACK);
        Product coffeeCup = new Product();
        coffeeCup.setCategory(mugs);
        List<Product> apparelProducts = new ArrayList<>();
        apparelProducts.add(polo);
        Mockito.doReturn(apparelProducts).when(productService).listByCategory("apparel");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/category/{category}", "apparel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    void getProductByIdFound() throws Exception {
        Category apparel = new Category("Apparel");
        Product polo = new Shirt(1L,"Briebug Logo Polo","Briebug Logo Polo: Black/LG", apparel, new BigDecimal(20),10, Size.LARGE, Color.BLACK);
        Mockito.doReturn(Optional.of(polo)).when(productService).get(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Briebug Logo Polo")))
                .andExpect(jsonPath("$.description", is("Briebug Logo Polo: Black/LG")))
                .andExpect(jsonPath("$.category.name", is("Apparel")))
                .andExpect(jsonPath("$.price", is(20)))
                .andExpect(jsonPath("$.qty", is(10)))
                .andExpect(jsonPath("$.size", is("LARGE")))
                .andExpect(jsonPath("$.color", is("BLACK")));

        Mockito.verify(productService, times(1)).get(1);
    }

    @Test
    void getProductByIdNotFound() throws Exception {
        Mockito.doReturn(Optional.empty()).when(productService).get(99);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}",99))
                .andExpect(status().isNotFound());

        Mockito.verify(productService, times(1)).get(99);
    }

    @Test
    void createValidRequestIsCreated() throws Exception {
        Category apparel = new Category("Apparel");
        Product product = new Shirt(1L,"Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Mockito.doNothing().when(productService).save(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated());
    }

    @Test
    void createInvalidRequestIsBadRequest() throws Exception {
        Product product = new Shirt();
        Mockito.doNothing().when(productService).save(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateValidRequestSuccess() throws Exception {
        Category apparel = new Category("Apparel");
        Product product = new Shirt(1L,"Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Mockito.doNothing().when(productService).update(product,1);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateInvalidRequestIsBadRequest() throws Exception {
        Product product = new Shirt();
        Mockito.doNothing().when(productService).save(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test()
    void updateInvalidProductIdNotFound() throws Exception {
        Category apparel = new Category("Apparel");
        Product product = new Shirt(1L,"Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE);
        Mockito.doThrow(new ResourceNotFoundException("product not found")).when(productService).update(product,99);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}",99)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNoContent());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> productService.update(product,99));
        assertTrue(exception.getMessage().contains("product not found"));
    }

    @Test
    void deleteValidProductIsDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/{id}",1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteInvalidProductIdNotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("product not found")).when(productService).delete(99);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/{id}",99))
                .andExpect(status().isNotFound());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> productService.delete(99));
        assertTrue(exception.getMessage().contains("product not found"));
    }
}