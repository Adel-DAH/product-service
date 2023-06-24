package com.product.provider.api.mapper;

import com.product.provider.api.dto.ProductDTO;
import com.product.provider.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    public ProductDTO toDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .reference(product.getReference())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(categoryMapper.toDTO(product.getCategory()))
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setReference(productDTO.getReference());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryMapper.toEntity(productDTO.getCategory()));
        return product;
    }
}
