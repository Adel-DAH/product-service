package com.product.provider.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.provider.api.dto.CategoryDTO;
import com.product.provider.api.dto.ProductDTO;
import com.product.provider.api.mapper.ProductMapper;
import com.product.provider.model.Category;
import com.product.provider.model.Product;
import com.product.provider.repository.CategoryRepository;
import com.product.provider.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest

class productControllerTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    private static final String API_URL = "/products";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Transactional
    void testGetProduct() throws Exception {
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

        mockMvc.perform((MockMvcRequestBuilders.get(API_URL+"/"+ product.getReference())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reference").value(product.getReference()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(product.getCategory()));
    }

    @Test
    @Transactional
    void testGetProductWithNoExistingProduct() throws Exception {

        mockMvc.perform((MockMvcRequestBuilders.get(API_URL+"/noExistingRef")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Unknown product noExistingRef"));
    }

    @Test
    @Transactional
    void testCreateProduct() throws Exception {

        Category category = new Category();
        category.setName("cat");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setReference("ref");
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(category);

        ProductDTO productToCreate = productMapper.toDTO(product);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reference").value(product.getReference()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(product.getCategory()));


    }

    @Test
    @Transactional
    void testCreateProductWithIdNotNull() throws Exception {

        Category category = new Category();
        category.setName("cat");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setId(1L);
        product.setReference("ref");
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(category);
        ProductDTO productToCreate = productMapper.toDTO(product);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("new product can't already have an id"));
    }

    @Test
    @Transactional
    void testCreateProductWithExistingRef() throws Exception {

        Category category = new Category();
        category.setName("cat");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setReference("ref");
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(category);
        productRepository.save(product);
        ProductDTO productToCreate = productMapper.toDTO(product);
        productToCreate.setId(null);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("reference already exists !"));
    }

    @Test
    @Transactional
    void testCreateProductWithNoExistingCategory() throws Exception {

        ProductDTO product= new ProductDTO();
        product.setReference("ref");
        product.setPrice(BigDecimal.TEN);
        product.setName("name");
        product.setDescription("description");
        product.setCategory(new CategoryDTO(Long.MAX_VALUE,""));

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Category doest exists !"));
    }
}