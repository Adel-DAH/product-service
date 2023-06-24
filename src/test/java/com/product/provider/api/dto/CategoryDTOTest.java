package com.product.provider.api.dto;

import com.product.provider.model.Category;
import com.product.provider.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOTest {

    @Test
    void testCategoryDto() {


        CategoryDTO category = new CategoryDTO();
        category.setId(1L);
        category.setName("name");
        category.setName("name");

        assertEquals(category.getName(),"name");
        assertEquals(category.getId(),1L);
        assertEquals(category.toString(),"CategoryDTO(id=1, name=name)");
        assertEquals(CategoryDTO.builder().id(1L).name("name").toString(),"CategoryDTO.CategoryDTOBuilder(id=1, name=name)");

        CategoryDTO category1 = new CategoryDTO(1L,"name");
        assertTrue(category1.canEqual(category));
        assertTrue(category1.equals(category));
        assertEquals(category1.hashCode(),category.hashCode());
    }

}