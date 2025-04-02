package com.luhtom.expensetracker.repository;

import com.luhtom.expensetracker.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM category WHERE name = ?", nativeQuery = true)
    Category findByName(@Param("name") String name);

}
