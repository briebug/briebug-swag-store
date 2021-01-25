package com.briebug.swag.service;

import com.briebug.swag.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * List all the products in the the database.
     *
     * @return All products in the database
     */
    List<Product> list();

    /**
     * Returns all products for a given category.
     *
     * @param category The category name to filter by
     * @return All products in the database for a given category name
     */
    List<Product> listByCategory(String category);

    /**
     * Saves the product to the database
     *
     * @param product The product to save
     */
    void save(Product product);

    /**
     * Retrieves a product by the specified id.
     *
     * @param id
     * @return
     */
    Optional<Product> get(long id);

    /**
     * Updates the specified product by its id
     *
     * @param product The updated product
     * @param id The id of the product being updated
     */
    void update(Product product, long id);

    /**
     * Deletes a product from the database.
     *
     * @param id The id of the product.
     */
    void delete(long id);

}
