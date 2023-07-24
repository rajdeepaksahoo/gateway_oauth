package com.lcwd.gateway.controllers;

import com.lcwd.gateway.models.AuthResponse;
import com.lcwd.gateway.models.User;
import com.lcwd.gateway.oktaconfig.OktaService;
import com.okta.sdk.resource.user.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private OktaService oktaService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody User user) {

        oktaService.createUser(user);

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("userId", user.getProfile().getEmail());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user,
            Model model
    ) {


        logger.info("user email id : {} ", user.getEmail());

        //creating auth response object
        AuthResponse authResponse = new AuthResponse();

        //setting email to authresponse
        authResponse.setUserId(user.getEmail());

        //setting toke to auth response
        authResponse.setAccessToken(client.getAccessToken().getTokenValue());

        authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());

        authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

        List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
            return grantedAuthority.getAuthority();
        }).collect(Collectors.toList());


        authResponse.setAuthorities(authorities);

        return new ResponseEntity<>(authResponse.getAccessToken(), HttpStatus.OK);


    }
    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "Wowww Home Page !!!";
    }

}
