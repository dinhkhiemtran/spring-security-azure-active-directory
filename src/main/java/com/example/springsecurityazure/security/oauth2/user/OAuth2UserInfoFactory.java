package com.example.springsecurityazure.security.oauth2.user;

import com.example.springsecurityazure.exception.OAuth2AuthenticationProcessingException;
import com.example.springsecurityazure.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.microsoft.toString())) {
            return new MicrosoftOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

}
