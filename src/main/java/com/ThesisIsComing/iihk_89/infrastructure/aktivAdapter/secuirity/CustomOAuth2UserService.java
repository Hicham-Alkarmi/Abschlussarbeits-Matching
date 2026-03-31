package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity;

import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerRoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class CustomOAuth2UserService implements OAuth2UserService {


    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();
    private final BetreuerRoleService betreuerRoleService;

    @Value("${app.admin-id}")
    private String adminId;

    public CustomOAuth2UserService(BetreuerRoleService betreuerRoleService) {
        this.betreuerRoleService = betreuerRoleService;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2User originalUser = defaultService.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
        Object idObj = originalUser.getAttribute("id");
        String githubId= String.valueOf(idObj);

        System.out.println("GitHub ID (numerisch): " + githubId);

        if (adminId.equals(githubId)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else if(betreuerRoleService.execute(githubId)){
            authorities.add(new SimpleGrantedAuthority("ROLE_BETREUER"));

        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "login");
    }
}
