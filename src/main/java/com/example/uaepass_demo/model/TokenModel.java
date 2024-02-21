package com.example.uaepass_demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {
    private String access_token;
    private String scope;
    private String token_type;
    private Integer expires_in;
}
