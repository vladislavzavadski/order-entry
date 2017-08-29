package com.netcracker.orderentry.catalog.service;

import com.netcracker.orderentry.catalog.domain.Category;
import com.netcracker.orderentry.catalog.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by ulza1116 on 8/18/2017.
 */
public interface CategoryService {
    Category createCategory(Category category);

    Category getCategory(int categoryId) throws NotFoundException;

    void deleteCategory(int categoryId) throws NotFoundException;

    Category updateCategory(Category category, int categoryId) throws NotFoundException;

    List<Category> createCategory(List<Category> categories);
}
