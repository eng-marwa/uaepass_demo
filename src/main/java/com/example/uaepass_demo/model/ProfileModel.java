package com.example.uaepass_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileModel {
    private String lastnameAR;
    private String sub;
    private String gender;
    private String firstnameEN;
    private String mobile;
    private String lastnameEN;
    private String fullnameEN;
    private String userType;
    private String firstnameAR;
    private String uuid;
    private String email;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

}
