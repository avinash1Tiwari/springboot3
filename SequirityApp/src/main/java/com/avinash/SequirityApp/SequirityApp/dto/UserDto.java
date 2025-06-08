package com.avinash.SequirityApp.SequirityApp.dto;

import com.avinash.SequirityApp.SequirityApp.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private Set<Role> roles;
}
