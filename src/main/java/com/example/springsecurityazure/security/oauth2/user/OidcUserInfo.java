package com.example.springsecurityazure.security.oauth2.user;

import java.util.Map;

public abstract class OidcUserInfo {

    protected Map<String, Object> attributes;

    public OidcUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
