package com.example.HotelManagement.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private String name;
    private String email;
    private String phone;
    private String hotel;
    private List<String> role;
    private String token;
    private long expiresIn;
}
