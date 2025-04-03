package com.luhtom.expensetracker.repository;

import com.luhtom.expensetracker.entity.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public List<Expense> findByCategoryId(Long id);

    @Query(value = "SELECT * FROM expense ORDER BY date ASC", nativeQuery = true)
    public List<Expense> findAllOrderByDateAsc();

    @Query(value = "SELECT * FROM expense ORDER BY date DESC", nativeQuery = true)
    public List<Expense> findAllOrderByDateDes();

    @Query(value = "SELECT * FROM expense WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC", nativeQuery = true)
    public List<Expense> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}