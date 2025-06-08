package com.avinash.project.uber.uberApp.configs;

import com.avinash.project.uber.uberApp.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

//
    private final String[] PUBLIC_ROUTES = {
            "/auth/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

//<=====================================================default security by spring===================================================>



//        1. here we need to add only one dependency :
//                                    		<dependency>
//                                                    <groupId>org.springframework.boot</groupId>
//                                                    <artifactId>spring-boot-starter-security</artifactId>
//                                            </dependency>



//        httpSecurity.
//                authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/posts","/error","/public/**","/auth/check-health").permitAll()
////                                .requestMatchers("/posts/**").hasAnyRole("USER")
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
////
////
//        return httpSecurity.build();



//<=====================================================================================================================================>









////        httpSecurity
////                .sessionManagement(sessionConfig -> sessionConfig
////                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .csrf(csrfConfig -> csrfConfig.disable())
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers(PUBLIC_ROUTES).permitAll()
//////                        .requestMatchers("/drivers/getMyRides","/errors","/public").permitAll()
//////                        .requestMatchers("/register/driver/*").hasRole("ADMIN")
//////                        .requestMatchers("/get/allrides").hasAnyRole("ADMIN","OWNER")
////                        .anyRequest().authenticated());
//
//
//


//<=====================================================customized security by JWT===============================================================>
//  1. add dependencies related to JWT.
//  2.  as in below disable csrftoken and enable STATELESS sessionCreationPolicy from the defaul one(default is STATEFUL)

        httpSecurity.sessionManagement((sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .csrf(csrfConfig ->csrfConfig.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ROUTES).permitAll()
                        .requestMatchers("register/driver/*").hasRole("ADMIN")
//                        .requestMatchers("/get/allrides").hasAnyRole("ADMIN","OWNER")
                        .anyRequest().authenticated())
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);                    ///// jwtAuthFilter is added


        return httpSecurity.build();




//<==============================================================================================================================================>

    }
//
}
