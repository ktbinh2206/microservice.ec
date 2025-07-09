package com.binhkt.ec_products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.binhkt.ec_products.models.Product;
import com.binhkt.ec_products.services.ProductService;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Map<String, Object> getProductListPage(Pageable pageable) {
        return productService.getProductListPage(pageable);
    }

    @PostMapping
    public String addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{productName}")
    public String deleteProduct(@PathVariable String productName) {
        return productService.deleteProduct(productName);
    }
}
