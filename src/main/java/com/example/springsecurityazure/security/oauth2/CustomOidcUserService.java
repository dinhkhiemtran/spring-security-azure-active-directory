package com.example.springsecurityazure.security.oauth2;

import com.example.springsecurityazure.exception.OAuth2AuthenticationProcessingException;
import com.example.springsecurityazure.model.AuthProvider;
import com.example.springsecurityazure.model.User;
import com.example.springsecurityazure.repo.UserRepository;
import com.example.springsecurityazure.security.UserPrincipal;
import com.example.springsecurityazure.security.oauth2.user.OAuth2UserInfo;
import com.example.springsecurityazure.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.springsecurityazure.security.oauth2.user.OidcUserInfo;
import com.example.springsecurityazure.security.oauth2.user.OidcUserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class CustomOidcUserService extends OidcUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        OidcUserInfo oidcUserInfo = (OidcUserInfo) OidcUserInfoFactory.getOidcUserInfo(userRequest.getClientRegistration().getRegistrationId(), oidcUser.getAttributes());
        if(StringUtils.isEmpty(oidcUserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oidcUserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oidcUserInfo);
        } else {
            user = registerNewUser(userRequest, oidcUserInfo);
        }

        return (OidcUser) UserPrincipal.create(user, oidcUser.getAttributes());
    }

    private User registerNewUser(OidcUserRequest userRequest, OidcUserInfo oidcUserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oidcUserInfo.getId());
        user.setName(oidcUserInfo.getName());
        user.setEmail(oidcUserInfo.getEmail());
        user.setImageUrl(oidcUserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OidcUserInfo oidUserInfo) {
        existingUser.setName(oidUserInfo.getName());
        existingUser.setImageUrl(oidUserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
