package com.avinash.project.uber.uberApp.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RiderDto {

    private Long id;
    private UserDto userDto;
    private Double rating;
}
