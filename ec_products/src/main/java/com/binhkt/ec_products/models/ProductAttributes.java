package com.binhkt.ec_products.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "product_attributes")
public class ProductAttributes {
    
    private String id;
    private String name;
    private String value;

    // Reference to the product this attribute belongs to
    private String productId;
}
