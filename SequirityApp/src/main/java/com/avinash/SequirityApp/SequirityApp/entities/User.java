package com.avinash.SequirityApp.SequirityApp.entities;

import com.avinash.SequirityApp.SequirityApp.enums.Permission;
import com.avinash.SequirityApp.SequirityApp.enums.Role;
import com.avinash.SequirityApp.SequirityApp.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_user")
@Getter
@Setter

@NoArgsConstructor      // For JPA
@AllArgsConstructor
@Audited
@Builder
@ToString                                           ///// to convert an java object into string so that we can read user details
public class User implements UserDetails {                     // UserDetails comes from springframework.security.core
//commit from 70
//    again commit from 70
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles;




    private String password;

    private String name;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//       return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(( "ROLE_" + role.name())))
//                .collect(Collectors.toSet());
////        return List.of();
//    }









//    6.4-a
////
//@ElementCollection(fetch = FetchType.EAGER)
//@Enumerated(EnumType.STRING)
//Set<Permission> permissions;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        Set<GrantedAuthority> authorities =  roles.stream()
//                .map(role -> new SimpleGrantedAuthority(( "ROLE_" + role.name())))
//                .collect(Collectors.toSet());
//
//        permissions.forEach(
//                permission -> authorities.add(new SimpleGrantedAuthority(permission.name()))
//        );
//
//        return authorities;
//    }






//6.4-b
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

           Set<SimpleGrantedAuthority> authorities = new HashSet<>();

           roles.forEach(
                   role -> {
                       Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthoritisForRole(role);
                       authorities.addAll(permissions);
                       authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
                   }
           );

        return authorities;
    }





    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
