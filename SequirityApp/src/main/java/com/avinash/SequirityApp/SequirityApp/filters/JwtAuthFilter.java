package com.avinash.SequirityApp.SequirityApp.filters;

import com.avinash.SequirityApp.SequirityApp.entities.User;
import com.avinash.SequirityApp.SequirityApp.services.JwtService;
import com.avinash.SequirityApp.SequirityApp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final HandlerExceptionResolver handlerExceptionResolver;

//    throws ServletException, IOException

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {


        try {
            final String requestTokenHeader = request.getHeader("Authorization");


            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);    /// allow to go to next filter
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];    //// token comes like "Bearer asfjfklvfl,lf"

            Long userId = jwtService.getUserIdFromToken(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(user, null, null);   // here we pass password = null, role = null.
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());   // here we pass password = null, role = user.getAuthorities() to provideautority to user.

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)                      /// / it helps to set users ip-address,seesion_id details,etc., i.e, user's device details is also set => helps to apply rate limiting and any ddos attack,etc.
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);      //// add this user in spring security context-holder.
            }

            filterChain.doFilter(request, response);
        } catch(Exception ex)
        {
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}
