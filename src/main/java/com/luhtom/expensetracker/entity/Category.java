package com.luhtom.expensetracker.entity;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Category {
    private Long id;
    private String name;
    private String description;
    private List<Expense> expenses;
}