package com.mile.demo.repository;

import com.mile.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryContainingIgnoreCase(String category);

    List<Product> findByCategoryContainingIgnoreCaseAndCreatedDateBetween(
            String category, LocalDate startDate, LocalDate endDate
    );

}
