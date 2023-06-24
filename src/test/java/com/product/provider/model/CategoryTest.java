package com.product.provider.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testCategory() {

        Product product = new Product();
        Category category = new Category();
        category.setId(1L);
        category.setName("name");
        product.setCategory(category);
        category.setName("name");
        category.setProducts(List.of(product));
        assertEquals(category.getProducts(),List.of(product));
        assertEquals(category.getName(),"name");
        assertEquals(category.getId(),1L);

        Category category1 = category;
        assertTrue(category1.canEqual(category));
        assertTrue(category1.equals(category));
    }




}