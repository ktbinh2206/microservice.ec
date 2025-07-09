package com.binhkt.ec_products.services.impl;

import com.binhkt.ec_products.models.Product;
import com.binhkt.ec_products.dto.ProductDTO;
import com.binhkt.ec_products.repository.ProductRepository;
import com.binhkt.ec_products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Map<String, Object> getProductListPage(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDTO> productDTOs = productPage.getContent().stream()
            .map(product -> {
                ProductDTO dto = new ProductDTO();
                dto.setId(product.getId());
                dto.setName(product.getName());
                dto.setDescription(product.getDescription());
                dto.setPrice(product.getPrice().doubleValue());
                dto.setStock(product.getStock());
                dto.setCategoryId(product.getCategoryId());
                dto.setSellerId(product.getSellerId());
                return dto;
            }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", productDTOs);
        response.put("totalElements", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        response.put("size", productPage.getSize());
        response.put("number", productPage.getNumber());
        response.put("sort", productPage.getSort());
        response.put("first", productPage.isFirst());
        response.put("last", productPage.isLast());
        response.put("numberOfElements", productPage.getNumberOfElements());
        response.put("empty", productPage.isEmpty());

        return response;
    }

    @Override
    public String addProduct(Product product) {
        productRepository.save(product);
        return "Product added successfully!";
    }

    @Override
    public String deleteProduct(String productName) {
        Product product = productRepository.findAll().stream()
            .filter(p -> p.getName().equals(productName))
            .findFirst()
            .orElse(null);

        if (product != null) {
            productRepository.delete(product);
            return "Product removed successfully!";
        } else {
            return "Product not found!";
        }
    }
}
