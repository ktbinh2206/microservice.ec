package com.binhkt.ec_products.services;

import com.binhkt.ec_products.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Map<String, Object> getProductListPage(Pageable pageable);

    String addProduct(Product product);

    String deleteProduct(String productName);

    List<Product> parseExcelFile(MultipartFile file);

    void saveProducts(List<Product> products);
}
