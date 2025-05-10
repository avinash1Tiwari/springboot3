package com.avinash.SequirityApp.SequirityApp.dto;


import com.avinash.SequirityApp.SequirityApp.enums.Permission;
import com.avinash.SequirityApp.SequirityApp.enums.Role;
import lombok.Data;

import java.util.Set;

@Data

public class SignUpDto {

    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;

}
