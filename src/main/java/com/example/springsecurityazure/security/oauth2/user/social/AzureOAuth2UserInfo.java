package com.example.springsecurityazure.security.oauth2.user.social;

import com.example.springsecurityazure.security.oauth2.user.OAuth2UserInfo;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public class AzureOAuth2UserInfo extends OAuth2UserInfo {

    public AzureOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((String) attributes.get("sub"));
    }

    @Override
    public String getName() {
        return (String) attributes.get("name") ;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("name") + "@khoabuidev213gmail.onmicrosoft.com";
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
