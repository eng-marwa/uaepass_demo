package com.example.uaepass_demo;

import com.example.uaepass_demo.model.TokenModel;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProfileAPI {


//    @Autowired
//    private OAuth2AuthorizedClientService authorizedClientService;


    //    @GetMapping("/loginSuccess")
//    public String loginSuccess(OAuth2AuthenticationToken authentication) {
//        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
//                authentication.getAuthorizedClientRegistrationId(),
//                authentication.getName());
//        // Here, you can access the access token from the authorized client
//        String accessToken = client.getAccessToken().getTokenValue();
//        // Use the access token to fetch user details from UAE Pass user info endpoint
//        // You may need to create a service for making HTTP requests to UAE Pass API
//        // and handle the response to extract user information
//        System.out.println(accessToken);
//        return "redirect:/profile";
//    }
//    @GetMapping("/login")
//    public String redirectToUAEPassLoginPage() {
//        String clientId = "your-client-id"; // Replace with your actual client ID
//        String redirectUri = "https://localhost:port/checkresponse"; // angular  page  your actual redirect URI
//        String uaePassAuthUrl = "https://stg-registration.uaepass.ae/?acr_values=urn:digitalid:authentication:flow:mobile&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code&scope=openid&ui_locales=en";
//        return "redirect:" + uaePassAuthUrl;
//    }
//
//    // restTemplet
//
//
//
//
    @GetMapping("/loginSuccess")
    public String handleUAEPassCallback(@RequestParam("code") String accessCode) {
        // Here you can process the access code, for example, save it to use later
        System.out.println("Access Code: " + accessCode);
        return "redirect:/profile"; // Redirect to the user profile page or any other page
    }


//    @GetMapping("/profile")
//    public String profile(Model model, OAuth2AuthenticationToken authentication) {
//        // Fetch user details from the authentication object
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        model.addAttribute("userName", oauth2User.getName());
//        model.addAttribute("userAttributes", oauth2User.getAttributes());
//        return "profile";
//    }


    //    @GetMapping("/checkresponse")
//    public String profile(Model model, OAuth2AuthenticationToken authentication) {
//        // Fetch user details from the authentication object
//        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//        model.addAttribute("userName", oauth2User.getName());
//        model.addAttribute("userAttributes", oauth2User.getAttributes());
//        return "profile";
//    }

    //https://stg-id.uaepass.ae/idshub/authorize?response_type=code&client_id=sandbox_stage&scope=urn:uae:digitalid:profile:general&state=HnlHOJTkTb66Y5H&redirect_uri=https://stg-selfcare.uaepass.ae&acr_values=urn:safelayer:tws:policies:authentication:level:low


    @GetMapping("/profile")
    public String profile(@RequestParam("token") String token) {
        String url = "https://stg-id.uaepass.ae/idshub/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            System.out.println("Response: " + responseBody);
        } else {
            System.err.println("Failed with status code: " + response.getStatusCode());
        }
        return "response: " + response.getBody();
    }


    @PostMapping("/token")
    public String getToken(@RequestParam("code") String accessCode) {
        System.out.println(accessCode);
        String url = "https://stg-id.uaepass.ae/idshub/token";

        // Define the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth("sandbox_stage", "sandbox_stage"); // Replace with your actual client credentials

        // Define the request body parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", "http://localhost:8080/token");
        requestBody.add("code", accessCode);
        // Create the HTTP entity with headers and body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        // Send the POST request
        ResponseEntity<TokenModel> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, TokenModel.class);
        // Print the response
        System.out.println("Response: " + responseEntity.getBody());

        return profile(responseEntity.getBody().getAccess_token());

    }

}
