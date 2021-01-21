package com.briebug.swag.controller;

import com.briebug.swag.exception.ResourceNotFoundException;
import com.briebug.swag.model.Product;
import com.briebug.swag.model.apparel.Shirt;
import com.briebug.swag.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> list() {
        return productService.list();
    }

    @GetMapping("/category/{category}")
    public List<Product> listByCategory(@PathVariable String category) {
        return productService.listByCategory(category);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves a product by id", notes = "I will return a Product by it's given ID.", response = Product.class)
    public Product get(@PathVariable Long id) {
        return productService
                .get(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id: " + id + " not found."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Shirt product) {
        productService.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Shirt product, @PathVariable long id) {
        productService.update(product,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

}
