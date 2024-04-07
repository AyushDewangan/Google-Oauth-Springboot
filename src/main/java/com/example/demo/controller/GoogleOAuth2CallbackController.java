package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
//@RequestMapping("/oauth2/callback/google")
public class GoogleOAuth2CallbackController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Autowired
    private RestTemplate restTemplate;

//    @GetMapping
//    public String googleCallback(@RequestParam("code") String code) {
//        String tokenEndpoint = "https://oauth2.googleapis.com/token";
//
//        String requestBody = "client_id=" + googleClientId + "&client_secret=" + googleClientSecret +
//            "&redirect_uri=" + googleRedirectUri + "&code=" + code + "&grant_type=authorization_code";
//        System.out.println("/token : "+requestBody);
//        AccessTokenResponse response = restTemplate.postForObject(tokenEndpoint, requestBody, AccessTokenResponse.class);
//        
//        // Here you can save the access token and use it to make authenticated requests
//        System.out.println(response);
//        return "redirect:" + "http://localhost:8080/auth"; // Redirect to home page or any other URL after successful authentication
//    }
    
    @GetMapping("/oauth2/callback/google")
    public String googleCallback(@RequestParam("code") String code) {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        System.out.println("Access Code : " + code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", googleClientId);
        requestBody.add("client_secret", googleClientSecret);
        requestBody.add("redirect_uri", googleRedirectUri);
        requestBody.add("code", code);
        requestBody.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenEndpoint, request, String.class);

        // Print response
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());

        return "redirect:http://localhost:8080/auth"; // Redirect to home page or appropriate URL after successful authentication
    }


    // Define a class to map the response from the token endpoint
    private static class AccessTokenResponse {
        private String access_token;
        private String token_type;
        private int expires_in;
        private String refresh_token;
        private String scope;
        
		@Override
		public String toString() {
			return "AccessTokenResponse [access_token=" + access_token + ", token_type=" + token_type + ", expires_in="
					+ expires_in + ", refresh_token=" + refresh_token + ", scope=" + scope + "]";
		}

        
        // Getters and setters
    }
}
