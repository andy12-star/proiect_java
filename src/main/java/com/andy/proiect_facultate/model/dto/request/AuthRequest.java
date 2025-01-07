package com.andy.proiect_facultate.model.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}