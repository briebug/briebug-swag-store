package com.briebug.swag.service;

import com.briebug.swag.exception.ResourceNotFoundException;
import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;
import com.briebug.swag.repository.CategoryRepository;
import com.briebug.swag.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable("all-products")
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> listByCategory(String categoryName) {
        Category category = categoryRepository.findByNameIgnoreCase(categoryName);
        return productRepository.findByCategory(category);
    }

    @Override
    public Optional<Product> get(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void update(Product product, long id) {
        if(productRepository.findById(id).isPresent()) {
            product.setId(id);
            save(product);
        } else {
            throw new ResourceNotFoundException("Product id: " + id + " not found.");
        }
    }

    @Override
    public void delete(long id) {
        if(productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product id: " + id + " not found.");
        }
    }

    @Override
    public void save(Product product) {
        product.setCategory(getCategory(product.getCategoryName()));
        productRepository.save(product);
    }

    private Category getCategory(String category) throws ResourceNotFoundException {
        Category cat = categoryRepository.findByNameIgnoreCase(category);
        if( cat == null) {
            throw new ResourceNotFoundException("Category: " + category + " not found.");
        }
        return cat;
    }
}
