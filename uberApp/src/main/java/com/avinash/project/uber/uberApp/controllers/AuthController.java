package com.avinash.project.uber.uberApp.controllers;

import com.avinash.project.uber.uberApp.dto.*;
import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.User;
import com.avinash.project.uber.uberApp.entities.enums.Role;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.avinash.project.uber.uberApp.repositories.UserRepository;
import com.avinash.project.uber.uberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${deploy.env}")
    private String deployEnv;

    private final AuthService authService;
    private final UserRepository userRepository;






    @PostMapping("/signup")
    UserDto signUp(@RequestBody SignUpDto signUpDto) {
        return authService.signUp(signUpDto);
    }






    @GetMapping("check-health")
    String checkHealth() {
        return "server is running fine";
    }





    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnboardDriverDto onboardDriverDto)
    {
       return new ResponseEntity<>(authService.onBoardNewDriver(userId,onboardDriverDto.getVehicleId()), HttpStatus.CREATED);
    }





    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response)
    {
             String tokens[] = authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        Cookie cookie = new Cookie("token",tokens[1]);
//        cookie.setSecure(true);                                  => it allows only https, but for lower environment we not enable it, b/c in lower(like by postman in local macheine, http is using)
        cookie.setSecure("production".equals(deployEnv));     ///// "production".equals(deployEnv) =>   deployEnv == "production" ? true : false  |||||||
                                                                ///When a cookie is marked as Secure, it is only sent over HTTPS connections, not over HTTP.
                                                                 /// This improves security by ensuring that sensitive cookies (like session IDs or auth tokens) can't be intercepted on unencrypted connections.

                                                                         // ,,, as on setting it true, it only allows https.
                                                                           // we enable it only on production => b/c in localEnvironemetn , only http is working


        cookie.setHttpOnly(true);

       response.addCookie(cookie);

             return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }



    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)
    {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie :: getValue)
                .orElseThrow(()->new AuthenticationServiceException("Refresh Token not found inside cookie"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
