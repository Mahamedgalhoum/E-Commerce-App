package com.e_Commerce.E_CommerceApp.errors;

public class AlreadyExistRecord extends RuntimeException {
    public AlreadyExistRecord(String message) {
        super(message);
    }
}
