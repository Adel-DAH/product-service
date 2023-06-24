package com.product.provider.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {


    @Test
    void testProductDTO(){

            CategoryDTO category = new CategoryDTO();
            category.setId(1L);
            category.setName("cat");

            ProductDTO product = new ProductDTO();
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
            assertEquals(product.toString(),"ProductDTO(id=1, reference=ref, name=name, description=description, price=10, category=CategoryDTO(id=1, name=cat))");

            assertEquals(

                    ProductDTO.builder()
                            .id(1L)
                            .reference("ref")
                            .name("name")
                            .description("description")
                            .price(BigDecimal.TEN)
                            .category(category)
                            .toString(),
                    "ProductDTO.ProductDTOBuilder(id=1, reference=ref, name=name, description=description, price=10, category=CategoryDTO(id=1, name=cat))"




            );

            ProductDTO product1 = new ProductDTO(1L,"ref","name","description",BigDecimal.TEN,category);
            assertTrue(product1.canEqual(product));
            assertTrue(product1.equals(product));
            assertEquals(product.hashCode(),product1.hashCode());


    }

}