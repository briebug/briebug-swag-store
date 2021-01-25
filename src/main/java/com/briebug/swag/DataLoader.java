package com.briebug.swag;

import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;
import com.briebug.swag.model.apparel.Color;
import com.briebug.swag.model.apparel.Size;
import com.briebug.swag.model.apparel.Shirt;
import com.briebug.swag.repository.CategoryRepository;
import com.briebug.swag.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        Category apparel = new Category("Apparel");
        categoryRepository.save(apparel);

        List<Product> products = new ArrayList<>();
        products.add(new Shirt("Briebug Logo Polo","Briebug Logo Polo: Black/SM", apparel, new BigDecimal(20),10, Size.SMALL, Color.BLACK));
        products.add(new Shirt("Briebug Logo Polo","Briebug Logo Polo: Black/MED", apparel, new BigDecimal(20),10, Size.MEDIUM, Color.BLACK));
        products.add(new Shirt("Briebug Logo Polo","Briebug Logo Polo: Black/LG", apparel, new BigDecimal(20),10, Size.LARGE, Color.BLACK));
        products.add(new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/SM", apparel, new BigDecimal(20),5, Size.SMALL, Color.ORANGE));
        products.add(new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/MED", apparel, new BigDecimal(20),5, Size.MEDIUM, Color.ORANGE));
        products.add(new Shirt("Briebug Logo Polo","Briebug Logo Polo: Orange/LG", apparel, new BigDecimal(20),5, Size.LARGE, Color.ORANGE));

        productRepository.saveAll(products);
    }

}
