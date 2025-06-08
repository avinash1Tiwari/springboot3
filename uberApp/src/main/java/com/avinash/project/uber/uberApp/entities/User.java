package com.avinash.project.uber.uberApp.entities;

import com.avinash.project.uber.uberApp.entities.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "uber_users",indexes = {
@Index(name = "idx_email",columnList = "email")
})
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_role_mapping",              // ✅ Custom name for the mapping table
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "user_roles")
    private Set<Role> user_roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user_roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
