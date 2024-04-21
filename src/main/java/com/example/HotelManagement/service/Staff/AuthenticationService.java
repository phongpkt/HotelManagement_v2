package com.example.HotelManagement.service.Staff;

import com.example.HotelManagement.dto.LoginUserDto;
import com.example.HotelManagement.dto.RegisterUserDto;
import com.example.HotelManagement.model.Role;
import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.repository.StaffRepository;
import com.example.HotelManagement.service.Hotel.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthenticationService {
    @Autowired
    private StaffRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public Staff authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public Staff signup(RegisterUserDto request) {
        try {
            Staff user = new Staff();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            hotelService.findById(request.getHotel_id()).ifPresent(user::setHotel);
            if (user.getRoles() == null) {
                user.setRoles(new HashSet<>());
            }
            if (!request.getRolesName().isEmpty()) {
                for (String roleName : request.getRolesName()) {
                    Role existingRole = roleService.findRoleByName(roleName);
                    if (existingRole != null) {
                        user.getRoles().add(existingRole);
                    }
                }
            }
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Staff findCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (Staff) authentication.getPrincipal();
    }
}
