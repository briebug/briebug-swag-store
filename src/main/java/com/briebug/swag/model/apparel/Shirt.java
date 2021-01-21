package com.briebug.swag.model.apparel;

import com.briebug.swag.model.Category;
import com.briebug.swag.model.Product;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Shirt extends Product {

    @NotNull
    private Size size;
    @NotNull
    private Color color;

    public Shirt() {
    }

    public Shirt(String name, String description, Category category, BigDecimal price, int qty, Size size, Color color) {
        this.setName(name);
        this.setDescription(description);
        this.setCategory(category);
        this.setPrice(price);
        this.setQty(qty);
        this.setSize(size);
        this.setColor(color);
    }

    public Shirt(long id, String name, String description, Category category, BigDecimal price, int qty, Size size, Color color) {
        this(name,description,category,price,qty,size,color);
        this.setId(id);
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TShirt{" +
                "size=" + size +
                ", color=" + color +
                '}';
    }
}
