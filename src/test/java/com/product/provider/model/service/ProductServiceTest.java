package com.product.provider.model.service;


import com.product.provider.api.dto.ProductDTO;
import com.product.provider.api.mapper.CategoryMapper;
import com.product.provider.api.mapper.ProductMapper;
import com.product.provider.model.Category;
import com.product.provider.model.Product;
import com.product.provider.repository.CategoryRepository;
import com.product.provider.repository.ProductRepository;

import com.product.provider.service.ProductService;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;


    @Test
    @Transactional
    void testFindByReferenceWithUnknownProduct() {
        assertThrows(NoSuchElementException.class, () -> productService.findByReference("unknown-Reference"));
    }

    @Test
    @Transactional
    void testFindByReferenceWithExistingProduct() {

        Category category = new Category();
        category.setName("cat");
        category = categoryRepository.save(category);

        String ref = "REF";
        Product product = new Product();
        product.setReference(ref);
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(category);
        productRepository.save(product);

        assertEquals(productMapper.toDTO(product), productService.findByReference(ref));
    }

    @Test
    @Transactional
    void testCreateProductWithIdNotNull() {

        ProductDTO productDTOToCreate = ProductDTO.builder()
                .id(Long.MAX_VALUE)
                .build();
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDTOToCreate));
    }

    @Test
    @Transactional
    void testCreateProductWithReferenceAlreadyExist() {

        Category category = new Category();
        category.setName("cat");
        category = categoryRepository.save(category);

        String ref = "REF";
        Product product = new Product();
        product.setReference(ref);
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(category);
        productRepository.save(product);

        ProductDTO productDTOToCreate = ProductDTO.builder()
                .reference(ref)
                .build();
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDTOToCreate));
    }

    @Test
    @Transactional
    void testCreateProduct() {

        Category category = new Category();
        category.setName("cat");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setReference("ref");
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(category);

        ProductDTO productCreated = productService.createProduct(productMapper.toDTO(product));

        assertEquals(productCreated.getReference(), product.getReference());
        assertEquals(productCreated.getCategory(), categoryMapper.toDTO(product.getCategory()));
        assertEquals(productCreated.getName(), product.getName());
        assertEquals(productCreated.getDescription(), product.getDescription());
        assertEquals(productCreated.getPrice(), product.getPrice());

    }
}