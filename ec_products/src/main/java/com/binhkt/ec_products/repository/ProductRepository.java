package com.binhkt.ec_products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.binhkt.ec_products.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
