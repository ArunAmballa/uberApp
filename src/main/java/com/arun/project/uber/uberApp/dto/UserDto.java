package com.arun.project.uber.uberApp.dto;

import com.arun.project.uber.uberApp.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;

    private String email;

    private Set<Role> roles;
}
