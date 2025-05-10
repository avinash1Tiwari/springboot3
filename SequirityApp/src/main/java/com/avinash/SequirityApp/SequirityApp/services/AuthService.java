package com.avinash.SequirityApp.SequirityApp.services;

import com.avinash.SequirityApp.SequirityApp.dto.LoginDto;
import com.avinash.SequirityApp.SequirityApp.dto.LoginResponseDto;
import com.avinash.SequirityApp.SequirityApp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
//        return jwtService.generateToken(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDto(user.getId(), accessToken,refreshToken);

    }

    public LoginResponseDto refreshToken(String refreshToken) {

           Long userId = jwtService.getUserIdFromToken(refreshToken);    //// taking userId from token
          User user = userService.getUserById(userId);

          sessionService.validateToken(refreshToken);                     /// it validates , if user has valid refreshToken during refreshing token.

          String accessToken = jwtService.generateAccessToken(user);

          return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}
