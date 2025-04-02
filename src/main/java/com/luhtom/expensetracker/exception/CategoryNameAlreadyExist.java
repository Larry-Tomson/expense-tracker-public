package com.luhtom.expensetracker.exception;

public class CategoryNameAlreadyExist extends RuntimeException {
    public CategoryNameAlreadyExist(String message) {
        super(message);
    }

}
