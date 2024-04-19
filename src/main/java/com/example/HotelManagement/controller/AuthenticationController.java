package com.example.HotelManagement.controller;

import com.example.HotelManagement.dto.LoginUserDto;
import com.example.HotelManagement.dto.RegisterUserDto;
import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.security.AuthenticationResponse;
import com.example.HotelManagement.security.JwtService;
import com.example.HotelManagement.service.Staff.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody RegisterUserDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User registered", service.signup(request))
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseObject> authenticate(@RequestBody LoginUserDto request) {
        Staff authenticatedUser = service.authenticate(request);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        AuthenticationResponse loginResponse = new AuthenticationResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User logged in", loginResponse)
        );
    }

}
