package com.example.finalproject.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {
    private String message;
    private boolean Successful;
}