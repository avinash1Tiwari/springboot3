package com.avinash.prod_ready_features.prod_ready_features.advices;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiResponse<T> {

    ////    @Pattern(regexp = "hh:mm:ss  dd:MM:yyyy")
//    @JsonFormat(pattern = "hh:mm:ss dd:MM:yyyy") =>> see error cause
    private LocalDate timestamp;

    private T data;

    private ApiError error;

    public ApiResponse()
    {
        this.timestamp = LocalDate.now();

    }

    public ApiResponse(T data){
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error)
    {
        this();
        this.error = error;
    }
}

