package com.mile.demo.controller;

import com.mile.demo.entity.Product;
import com.mile.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public List<Product> getProducts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {

        LocalDate start = null;
        LocalDate end = null;

        if (startDate != null && !startDate.isEmpty()) {
            start = LocalDate.parse(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            end = LocalDate.parse(endDate);
        }

        return service.getFiltered(search, start, end);
    }


    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return service.save(product);
    }
}
