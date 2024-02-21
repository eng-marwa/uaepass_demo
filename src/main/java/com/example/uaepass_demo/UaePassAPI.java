package com.example.uaepass_demo;

import com.example.uaepass_demo.model.TokenModel;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class UaePassAPI {

    private String genratedURL() {
        String uaePass = "https://stg-id.uaepass.ae/idshub/authorize?";
        String responseType = "code";
        String scope = "urn:uae:digitalid:profile:general";
        String state = "HnlHOJTkTb66Y5H";
        String otherQuery = "urn:safelayer:tws:policies:authentication:level:low";
        String clientId = "sandbox_stage";
        String clientSecret = "sandbox_stage";
        String redirectUri = "http://localhost:8080/token";
        return uaePass + "response_type=" + responseType + "&client_id=" + clientId + "&scope=" + scope + "&state=" + state + "&redirect_uri=" + redirectUri + "&acr_values=" + otherQuery;
    }

    @GetMapping("/login")
    public String login() {
        String url = genratedURL();
        return "redirect:" + url;
    }



}
