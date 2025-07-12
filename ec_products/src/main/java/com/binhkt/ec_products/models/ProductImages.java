package com.binhkt.ec_products.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "product_images")
public class ProductImages {

    private String id;
    private String imageUrl;
    private String productId;

    // Additional fields can be added as needed, such as image type, alt text, etc.
    private String altText;
    private String imageType; // e.g., thumbnail, main image, gallery image
}
