package com.example.HotelManagement.controller;

import com.example.HotelManagement.dto.LoginUserDto;
import com.example.HotelManagement.dto.RegisterUserDto;
import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Role;
import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.security.AuthenticationResponse;
import com.example.HotelManagement.security.JwtService;
import com.example.HotelManagement.service.Staff.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

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
        String user_name = authenticatedUser.getFirstName() + " " +  authenticatedUser.getLastName();
        AuthenticationResponse loginResponse = getAuthenticationResponse(authenticatedUser, user_name, jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User logged in", loginResponse)
        );
    }

    private AuthenticationResponse getAuthenticationResponse(Staff authenticatedUser, String user_name, String jwtToken) {
        List<String> rolesList = new ArrayList<>();
        for (Role role : authenticatedUser.getRoles()) {
            rolesList.add(role.getName());
        }

        AuthenticationResponse loginResponse = new AuthenticationResponse(
                user_name,
                authenticatedUser.getEmail(),
                authenticatedUser.getPhone(),
                authenticatedUser.getHotel().getAddress(),
                rolesList,
                jwtToken,
                jwtService.getExpirationTime());
        return loginResponse;
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> userProfile(@RequestHeader("authentication") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User logged in", service.findCurrentUser(token))
        );
    }
}
