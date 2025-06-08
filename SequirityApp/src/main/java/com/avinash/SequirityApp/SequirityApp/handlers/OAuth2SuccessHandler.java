// this service is used for google-login functionality


package com.avinash.SequirityApp.SequirityApp.handlers;
import com.avinash.SequirityApp.SequirityApp.entities.User;
import com.avinash.SequirityApp.SequirityApp.services.JwtService;
import com.avinash.SequirityApp.SequirityApp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Value("${deploy.env}")
    private String deployEnv;

    private final UserService userService;
    private final JwtService jwtService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User)token.getPrincipal();

        log.info(oAuth2User.getAttribute("email"));
        String email = oAuth2User.getAttribute("email");

        User user = userService.getUserByEmail(email);

        if(user == null)
        {
            User newUser = User.builder()
                .name(oAuth2User.getAttribute("email"))
                .email(email).
                build();

            user = userService.save(newUser);
        }



//        generte token

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);               /// prevents any javascript injection, only http request allowed



        cookie.setSecure("production".equals(deployEnv));     ///// "production".equals(deployEnv) =>   deployEnv == "production" ? true : false
        // ,,, as on setting it true, it only allows https.
        // we enable it only on production => b/c in localEnvironemetn , only http is working

        response.addCookie(cookie);


        String frontendUrl = "http://localhost:9000/home.html?token="+accessToken;             /// add this "/home.html" in securityChainUrl so that we don't redirect to login(whitelisting)
        getRedirectStrategy().sendRedirect(request,response,frontendUrl);

    }
}
