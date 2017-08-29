package com.netcracker.orderentry.catalog.controller;

import com.netcracker.orderentry.catalog.domain.Category;
import com.netcracker.orderentry.catalog.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ulza1116 on 8/18/2017.
 */
@RestController
@Api
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public void createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable("categoryId") int categoryId){
        return categoryService.getCategory(categoryId);
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("categoryId") int categoryId){
        categoryService.deleteCategory(categoryId);
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.PUT)
    public void updateCategory(@RequestBody Category category, @PathVariable("categoryId") int categoryId) {
        categoryService.updateCategory(category, categoryId);
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.POST)
    public void createCategory(@RequestBody List<Category> categories){
        categoryService.createCategory(categories);
    }

}
