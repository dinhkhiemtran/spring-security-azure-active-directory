package com.example.springsecurityazure.security.oauth2.user;

import com.example.springsecurityazure.exception.OAuth2AuthenticationProcessingException;
import com.example.springsecurityazure.model.AuthProvider;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;

public class OidcUserInfoFactory {

    public static OidcUser getOidcUserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.microsoft.toString())) {
            return (OidcUser) new MicrosoftOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
