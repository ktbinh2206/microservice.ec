package com.binhkt.ec_products.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String categoryId;
    private String sellerId;
}
