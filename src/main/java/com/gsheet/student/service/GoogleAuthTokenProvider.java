package com.gsheet.student.service;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.OAuth2Credentials;
import com.gsheet.student.exception.GoogleCredentialRefreshException;
import com.gsheet.student.util.Messages;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Log4j2
public class GoogleAuthTokenProvider implements AuthTokenProvider {

    private static GoogleAuthTokenProvider instance;

    private final Resource credentialsResource;

    private final List<String> scopes;

    private GoogleCredentials currentCredentials;

    private GoogleAuthTokenProvider(Resource credentialsResource, List<String> scopes) {
        this.credentialsResource = credentialsResource;
        this.scopes = scopes;
    }

    public static GoogleAuthTokenProvider create(Resource credentialsResource, List<String> scopes) {
        if (instance == null) {
            instance = new GoogleAuthTokenProvider(credentialsResource, scopes);
        }

        return instance;
    }

    public String getAccessToken() {
        initCredentials();

        AccessToken accessToken = currentCredentials.getAccessToken();

        if (accessToken == null) {
            throw new GoogleCredentialRefreshException();
        }

        return accessToken.getTokenValue();
    }

    private void initCredentials() {
        if (currentCredentials == null) {
            try {
                currentCredentials = GoogleCredentials.fromStream(credentialsResource.getInputStream())
                    .createScoped(scopes);

                currentCredentials.addChangeListener(OAuth2Credentials::refreshIfExpired);
                currentCredentials.refreshIfExpired();
            } catch (IOException e) {
                log.error(Messages.getMessageForLocale("google.auth.error.token-update"));
                throw new GoogleCredentialRefreshException();
            }
        }
    }
}
