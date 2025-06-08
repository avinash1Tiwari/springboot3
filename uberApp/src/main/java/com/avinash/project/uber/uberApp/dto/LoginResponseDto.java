package com.avinash.project.uber.uberApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String accessToken;
}
