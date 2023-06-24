package com.product.provider.api.mapper;


import com.product.provider.api.dto.CategoryDTO;
import com.product.provider.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


    public CategoryDTO toDTO(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName()).build();
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}
