package com.binhkt.ec_products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.binhkt.ec_products.dto.ErrorDTO;
import com.binhkt.ec_products.dto.ResponseDTO;
import com.binhkt.ec_products.models.Product;
import com.binhkt.ec_products.services.ProductService;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getProductListPage(Pageable pageable) {
        try {
            Map<String, Object> data = productService.getProductListPage(pageable);
            return ResponseEntity.ok(new ResponseDTO(true, "Products retrieved successfully", data, null, Instant.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO(false, "Failed to retrieve products", null, new ErrorDTO("GET_PRODUCTS_ERROR", e.getMessage()), Instant.now().toString()));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addProduct(@RequestBody Product product) {
        try {
            String message = productService.addProduct(product);
            return ResponseEntity.ok(new ResponseDTO(true, message, null, null, Instant.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO(false, "Failed to add product", null, new ErrorDTO("ADD_PRODUCT_ERROR", e.getMessage()), Instant.now().toString()));
        }
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable String productName) {
        try {
            String message = productService.deleteProduct(productName);
            return ResponseEntity.ok(new ResponseDTO(true, message, null, null, Instant.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO(false, "Failed to delete product", null, new ErrorDTO("DELETE_PRODUCT_ERROR", e.getMessage()), Instant.now().toString()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadProductsFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Product> products = productService.parseExcelFile(file);
            productService.saveProducts(products);
            return ResponseEntity.ok(new ResponseDTO(true, "Products uploaded successfully", products, null, Instant.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO(false, "Failed to upload products", null, new ErrorDTO("UPLOAD_PRODUCTS_ERROR", e.getMessage()), Instant.now().toString()));
        }
    }
}
