package com.apitool.apitool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private int statusCode;
    private String responseBody;
}
