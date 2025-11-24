package com.e_Commerce.E_CommerceApp.errors;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
