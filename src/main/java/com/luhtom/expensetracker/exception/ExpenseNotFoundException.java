package com.luhtom.expensetracker.exception;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Long id) {
        super("Expense id not found for " + id);
    }
}
