package com.avinash.project.uber.uberApp.advices;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
@Data
@NoArgsConstructor

public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return true;               // true signifies all body response will go to convert in proper json
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

//        if(request.getURI().getPath().contains("/v3/api-docs")) return body;

        List<String> allowedRoutes = List.of("/v3/api-docs","/actuator");                 ///// localhost:9000/v3/api-docs, localhost:9000/actuator

        boolean isAllowed = allowedRoutes
                .stream()
                .anyMatch(route -> request.getURI().getPath().contains(route));

        if (body instanceof ApiResponse<?> || isAllowed) {
            return body;
        }

        if (body instanceof String) {
            return body;
        }

        return new ApiResponse<>(body);
    }

//  http://localhost:9000/swagger-ui/index.html#/   => for swagger UI
}