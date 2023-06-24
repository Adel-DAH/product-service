package com.product.provider.service;

import com.product.provider.model.Category;
import com.product.provider.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    public Boolean existsById(long id) {
        return categoryRepository.existsById(id);
    }
}
