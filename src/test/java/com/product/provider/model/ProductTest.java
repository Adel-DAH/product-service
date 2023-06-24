package com.product.provider.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProduct() {

        Category category = new Category();

        Product product = new Product();
        product.setId(1L);
        product.setReference("ref");
        product.setName("name");
        product.setDescription("description");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(category);
        assertEquals(product.getId(), 1L);
        assertEquals(product.getReference(), "ref");
        assertEquals(product.getName(), "name");
        assertEquals(product.getDescription(), "description");
        assertEquals(product.getPrice(), BigDecimal.TEN);
        assertEquals(product.getCategory(), category);
        assertEquals(product.toString(),"Product(id=1, reference=ref, name=name, description=description, price=10, category=Category(id=null, name=null, products=null))");

        Product product1 = product;
        assertTrue(product1.canEqual(product));
        assertTrue(product1.equals(product));
        assertEquals(product1.hashCode(),product.hashCode());

    }
}