package com.product.provider.service;

import com.product.provider.api.dto.ProductDTO;
import com.product.provider.api.mapper.ProductMapper;
import com.product.provider.model.Category;
import com.product.provider.model.Product;
import com.product.provider.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    private void initDataBase() {

        if (productRepository.findAll().isEmpty()) {
            Category category = new Category();
            category.setName("Food");
            category = categoryService.save(category);

            addProductToDB("Milka", "prod01", "this is our Milka description", BigDecimal.TEN, category);
            addProductToDB("Cheese", "prod02", "this is our Cheese description", new BigDecimal(5), category);
        }

    }

    public ProductDTO findByReference(String reference) {

        return productRepository.findOneByReference(reference).map(productMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Unknown product " + reference));
    }

    private Product addProductToDB(String name, String ref, String desc, BigDecimal price, Category category) {
        Product prod = new Product();
        prod.setName(name);
        prod.setReference(ref);
        prod.setDescription(desc);
        prod.setPrice(price);
        prod.setCategory(category);
        return productRepository.save(prod);
    }

    public ProductDTO createProduct(ProductDTO product) {

        if (product.getId() != null) {
            throw new IllegalArgumentException("new product can't already have an id");
        }

        if (productRepository.findOneByReference(product.getReference()).isPresent()) {
            throw new IllegalArgumentException("reference already exists !");
        }

        if (!categoryService.existsById(product.getCategory().getId())) {
            throw new IllegalArgumentException("Category doest exists !");
        }

        Product productToSave = productMapper.toEntity(product);

        return productMapper.toDTO(productRepository.save(productToSave));

    }
}
