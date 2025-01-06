package com.arun.project.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private double[] coordinates;
    private String type="Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
