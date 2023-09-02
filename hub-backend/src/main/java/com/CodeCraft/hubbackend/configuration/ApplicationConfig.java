package com.CodeCraft.hubbackend.configuration;

import com.CodeCraft.hubbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.ExecutionException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            try {
                return userService.findByUsernameRepo(username);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
