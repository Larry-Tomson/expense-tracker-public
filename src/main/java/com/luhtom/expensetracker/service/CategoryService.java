package com.luhtom.expensetracker.service;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.luhtom.expensetracker.entity.Category;
import com.luhtom.expensetracker.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category categoryModel = category.get();
            categoryModel.setName(categoryDetails.getName());
            categoryModel.setDescription(categoryDetails.getDescription());
            return categoryRepository.save(categoryModel);
        }
        return null;
    }

    public boolean deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent() && !category.get().getName().equals("Uncategorized")) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Category getDefaultCategory() {
        return categoryRepository.findByName("Uncategorized");
    }
}
