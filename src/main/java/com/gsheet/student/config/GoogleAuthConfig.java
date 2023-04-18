package com.gsheet.student.config;

import com.gsheet.student.service.AuthTokenProvider;
import com.gsheet.student.service.GoogleAuthTokenProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "google.auth")
@Getter
@Setter
public class GoogleAuthConfig {

    private String credentialFilePath;

    private List<String> scopes = new ArrayList<>();

    @Bean
    public AuthTokenProvider googleAuthTokenProvider() {
        Resource credentialsResource = new ClassPathResource(credentialFilePath);

        return GoogleAuthTokenProvider.create(credentialsResource, scopes);
    }
}
