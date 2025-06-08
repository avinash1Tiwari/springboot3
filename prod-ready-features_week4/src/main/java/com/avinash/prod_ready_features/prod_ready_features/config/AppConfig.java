package com.avinash.prod_ready_features.prod_ready_features.config;


import com.avinash.prod_ready_features.prod_ready_features.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareIpml")
public class AppConfig {

    @Bean
    ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }

    @Bean
    AuditorAware<String> getAuditorAwareIpml()
    {
        return new AuditorAwareImpl();
    }
}
