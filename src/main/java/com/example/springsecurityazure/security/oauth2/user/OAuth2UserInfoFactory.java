package com.example.springsecurityazure.security.oauth2.user;

import com.example.springsecurityazure.exception.OAuth2AuthenticationProcessingException;
import com.example.springsecurityazure.model.AuthProvider;
import com.example.springsecurityazure.security.oauth2.user.social.AzureOAuth2UserInfo;
import com.example.springsecurityazure.security.oauth2.user.social.FacebookOAuth2UserInfo;
import com.example.springsecurityazure.security.oauth2.user.social.GithubOAuth2UserInfo;
import com.example.springsecurityazure.security.oauth2.user.social.GoogleOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.azure.toString()))
        {
            return new AzureOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.google.toString()))
        {
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString()))
        {
        return new GithubOAuth2UserInfo(attributes);
        }
        else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString()))
        {
            return new FacebookOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
