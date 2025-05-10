package com.avinash.prod_ready_features.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
//    AuditorAware<String>   => here we took String dataType, we can take int,float,etc => it's just id fro auditor/admin

    @Override
    public Optional<String> getCurrentAuditor() {
//        1. get security context
//        2. get authentication
//        3. get the principle
//        4. get the username
        return Optional.of("Avinash Tiwari");
    }
}
