package com.avinash.project.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    private double[] coordinates;
    private String type = "Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public PointDto(String type, double[] coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

}
