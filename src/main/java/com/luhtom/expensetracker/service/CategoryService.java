package com.luhtom.expensetracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.luhtom.expensetracker.entity.Category;
import com.luhtom.expensetracker.entity.Expense;
import com.luhtom.expensetracker.exception.CategoryNameAlreadyExist;
import com.luhtom.expensetracker.exception.CategoryNotFoundException;
import com.luhtom.expensetracker.repository.CategoryRepository;
import com.luhtom.expensetracker.repository.ExpenseRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Expense not found, id:" + id));

    }

    public Category createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) != null) {
            throw new CategoryNameAlreadyExist("Category name already exist: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {

        Category category = getCategoryById(id);

        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());

        return categoryRepository.save(category);
    }

    public boolean deleteCategory(Long id) {
        Category category = getCategoryById(id);
        boolean isDefaultCategory = category.getName().equals("Uncategorized");

        if (!isDefaultCategory) {

            List<Expense> expenses = expenseRepository.findByCategoryId(id);
            for (Expense expense : expenses) {
                expense.setCategory(getDefaultCategory());
                expenseRepository.save(expense);
            }
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Category getDefaultCategory() {
        // TODO the only use case to reset to Uncategorized might remove
        return categoryRepository.findByName("Uncategorized");
    }
}
