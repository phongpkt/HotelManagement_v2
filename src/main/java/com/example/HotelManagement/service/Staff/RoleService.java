package com.example.HotelManagement.service.Staff;

import com.example.HotelManagement.model.Role;
import com.example.HotelManagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
