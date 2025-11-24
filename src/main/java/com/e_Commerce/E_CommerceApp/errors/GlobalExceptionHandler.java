package com.e_Commerce.E_CommerceApp.errors;


import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(AlreadyExistRecord.class)
    public ResponseEntity<ApiResponse> handleAlreadyExist(AlreadyExistRecord ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Internal Server Error: " + ex.getMessage(), null));
    }
}
