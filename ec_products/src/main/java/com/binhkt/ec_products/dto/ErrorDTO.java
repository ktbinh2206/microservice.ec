package com.binhkt.ec_products.dto;

public class ErrorDTO {
    private String code;
    private String details;

    public ErrorDTO(String code, String details) {
        this.code = code;
        this.details = details;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
