package com.avinash.project.uber.uberApp.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApiError {
    private String message;

    private HttpStatus status;

    List<String> subErrors;
}
