package com.apitool.apitool.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ApiRequest {
    private String url;
    private String method;
    private String body;
   /* private Map<String, String> headers;
    private Map<String, String> queryParams;*/
}
