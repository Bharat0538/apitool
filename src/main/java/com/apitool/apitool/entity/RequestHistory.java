package com.apitool.apitool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;
    private String url;

    @Lob
    private String requestBody;

    @Lob
    private String responseBody;

    private int statusCode;
    private LocalDateTime timestamp;
}
