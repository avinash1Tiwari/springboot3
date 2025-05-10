package com.avinash.SequirityApp.SequirityApp.controllers;

import com.avinash.SequirityApp.SequirityApp.dto.LoginResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import com.avinash.SequirityApp.SequirityApp.dto.LoginDto;
import com.avinash.SequirityApp.SequirityApp.dto.SignUpDto;
import com.avinash.SequirityApp.SequirityApp.dto.UserDto;
import com.avinash.SequirityApp.SequirityApp.services.AuthService;
import com.avinash.SequirityApp.SequirityApp.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    @Value("${deploy.env}")
    private String deployEnv;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody  SignUpDto signUpDto)
    {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response )
    {
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
                                                                                     ////     When you store the JWT in a cookie (especially HttpOnly and Secure),
                                                                                         //// the browser will automatically attach that cookie to every request made to the same origin or allowed domain, without you needing
                                                                                           ///// to manually store or send it from your frontend (like Redux or headers).


        cookie.setHttpOnly(true);               /// prevents any javascript injection, only http request allowed
                                                   //// Without HttpOnly:
                                                   //// If a malicious script runs in your user’s browser (via XSS), it can read the cookie:   => document.cookie





        cookie.setSecure("production".equals(deployEnv));     ///// "production".equals(deployEnv) =>   deployEnv == "production" ? true : false  |||||||
                                                                 ///When a cookie is marked as Secure, it is only sent over HTTPS connections, not over HTTP.
                                                                 /// This improves security by ensuring that sensitive cookies (like session IDs or auth tokens) can't be intercepted on unencrypted connections.

                                                                   // ,,, as on setting it true, it only allows https.
                                                                    // we enable it only on production => b/c in localEnvironemetn , only http is working


        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }



    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)
    {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie :: getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside the cookies"));

        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
