package com.avinash.project.uber.uberApp.advices;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor

public class ApiResponse<T> {

    private LocalDate timestamp;

    private T data;

    private ApiError error;

    public ApiResponse() {
        this.timestamp = LocalDate.now();

    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }


}
