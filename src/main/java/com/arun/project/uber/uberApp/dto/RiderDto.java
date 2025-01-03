package com.arun.project.uber.uberApp.dto;

import com.arun.project.uber.uberApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiderDto {

    private Double rating;
    private UserDto user;
}
