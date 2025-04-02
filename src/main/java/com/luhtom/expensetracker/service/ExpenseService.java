package com.luhtom.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.luhtom.expensetracker.entity.Category;
import com.luhtom.expensetracker.entity.Expense;
import com.luhtom.expensetracker.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryService categoryService;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryService categoryService) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByDateRange(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseRepository.findByDateRange(startDate, endDate);
    }

    public List<Expense> getAllExpensesSortedByDate(String sort) {
        if ("asc".equalsIgnoreCase(sort)) {
            return expenseRepository.findAllOrderByDateAsc();
        } else {
            return expenseRepository.findAllOrderByDateDes();
        }
    }

    public List<Expense> getExpensesByCategory(Long categoryId) {
        return expenseRepository.findByCategoryId(categoryId);
    }

    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            return expense.get();
        }
        return null; // ?
    }

    public Expense createExpense(Expense expense) {
        if (expense.getCategory() == null) {
            Category defaultCategory = categoryService.getDefaultCategory();
            expense.setCategory(defaultCategory);
        }
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            Expense expenseModel = expense.get();

            expenseModel.setDescription(expenseDetails.getDescription());
            expenseModel.setAmount(expenseDetails.getAmount());
            expenseModel.setDate(expenseDetails.getDate());

            if (expenseDetails.getCategory() != null) {
                expenseModel.setCategory(expenseDetails.getCategory());
            }

            return expenseRepository.save(expenseModel);
        }
        return null;
    }

    public boolean deleteExpense(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
