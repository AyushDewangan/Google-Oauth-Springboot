package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

//import com.example.demo.controller.GoogleOAuth2CallbackController.AccessTokenResponse;

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
    
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/authorize/google")
    public String redirectToGoogleForAuth() {
    	System.out.println("calling redirectToGoogleForAuth method");
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + googleClientId +
                "&redirect_uri=" + googleRedirectUri +
                "&response_type=code" +
                "&scope=openid%20email%20profile"; // Adjust scope as needed
        System.out.println("googleAuthUrl : " + googleAuthUrl);
        
        // new code
//        String response = restTemplate.getForObject(googleAuthUrl, null, String.class);
        
        return "redirect:" + googleAuthUrl;
//    	return "11";
    }
}
