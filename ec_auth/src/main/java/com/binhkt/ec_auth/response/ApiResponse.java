package com.binhkt.ec_auth.response;

import java.time.Instant;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private Object error;
    private String timestamp;

    public ApiResponse(boolean success, String message, Object data, Object error) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
        this.timestamp = Instant.now().toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Object getError() {
        return error;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
