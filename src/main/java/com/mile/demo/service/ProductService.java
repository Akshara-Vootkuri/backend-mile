package com.mile.demo.service;

import com.mile.demo.entity.Product;
import com.mile.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getFiltered(String category, LocalDate start, LocalDate end) {


        if (start == null || end == null) {
            return productRepository.findByCategoryContainingIgnoreCase(category);
        }


        return productRepository.findByCategoryContainingIgnoreCaseAndCreatedDateBetween(
                category, start, end
        );
    }

    public Product save(Product p) { return productRepository.save(p); }
}
