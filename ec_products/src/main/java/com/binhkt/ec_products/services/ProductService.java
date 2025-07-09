package com.binhkt.ec_products.services;

import com.binhkt.ec_products.models.Product;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductService {

    Map<String, Object> getProductListPage(Pageable pageable);

    String addProduct(Product product);

    String deleteProduct(String productName);
}
