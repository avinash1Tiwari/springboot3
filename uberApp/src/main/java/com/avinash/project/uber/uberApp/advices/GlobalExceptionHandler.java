package com.avinash.project.uber.uberApp.advices;


import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.exceptions.RuntimeConflictException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Data
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }

@ExceptionHandler(AuthenticationException.class)
public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex)
{
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.UNAUTHORIZED)
            .message(ex.getLocalizedMessage())
            .build();

    return buildErrorResponseEntity(apiError);
}


    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflictException(RuntimeConflictException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationError(MethodArgumentNotValidException exception) {

        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(errors)
                .build();


        return buildErrorResponseEntity(apiError);
    }


    public ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());

    }
}
