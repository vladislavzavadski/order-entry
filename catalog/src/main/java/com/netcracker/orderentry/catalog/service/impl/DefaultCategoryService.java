package com.netcracker.orderentry.catalog.service.impl;

import com.netcracker.orderentry.catalog.domain.Category;
import com.netcracker.orderentry.catalog.repository.CategoryRepository;
import com.netcracker.orderentry.catalog.service.CategoryService;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ulza1116 on 8/18/2017.
 */
@Service
public class DefaultCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(int categoryId) throws NotFoundException {
        Category category = categoryRepository.findOne(categoryId);

        if (category == null){
            throw new NotFoundException("Category with id = "+categoryId+" was not found.");
        }

        return category;
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public void deleteCategory(int categoryId) throws NotFoundException {

        if(!categoryRepository.exists(categoryId)){
            throw new NotFoundException("Category with id = " + categoryId + " was not found");
        }

        categoryRepository.delete(categoryId);
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public Category updateCategory(Category category, int categoryId) throws NotFoundException {
        Category storedCategory = categoryRepository.findOne(categoryId);

        if(storedCategory == null){
            throw new NotFoundException("Category with id = " + categoryId + " was not found");
        }

        storedCategory.setName(category.getName());
        return categoryRepository.save(storedCategory);
    }

    @Override
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public List<Category> createCategory(List<Category> categories){
        return categoryRepository.save(categories);
    }

}
