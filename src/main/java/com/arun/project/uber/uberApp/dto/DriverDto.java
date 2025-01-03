package com.arun.project.uber.uberApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private UserDto user;

    private Double rating;

    private Boolean available;

}
