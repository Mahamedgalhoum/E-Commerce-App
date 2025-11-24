package com.e_Commerce.E_CommerceApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;
}
