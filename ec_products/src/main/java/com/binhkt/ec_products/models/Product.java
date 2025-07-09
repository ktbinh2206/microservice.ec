package com.binhkt.ec_products.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String categoryId;

    private String sellerId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
