package com.briebug.swag.service;

import com.briebug.swag.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> list();

    List<Product> listByCategory(String category);

    void save(Product product);

    Optional<Product> get(long id);

    void update(Product product, long id);

    void delete(long id);

}
