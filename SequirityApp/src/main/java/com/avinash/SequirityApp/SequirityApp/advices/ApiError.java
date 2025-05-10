package com.avinash.SequirityApp.SequirityApp.advices;

import lombok.Builder;              //// it helps to make this class object
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
@Builder
public class ApiError {
    private String message;
    private HttpStatus status;
    List<String> subErrors;
}
