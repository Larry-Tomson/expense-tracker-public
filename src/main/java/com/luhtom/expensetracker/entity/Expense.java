package com.luhtom.expensetracker.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Expense {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    private Category category;
}
