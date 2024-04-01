package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> findAll(){
        return staffRepository.findAll();
    }
    public Staff save(Staff newStaff){
        return staffRepository.save(newStaff);
    }
    public Optional<Staff> findById(Long id){
        return staffRepository.findById(id);
    }
    public boolean findDuplication(Staff staff){
        List<Staff> foundResource = staffRepository.findByEmail(staff.getEmail().trim());
        if(foundResource.isEmpty()) {
            return false;
        }
        return true;
    }
    public Staff update(Staff newStaff, Long id){
        Staff updatedStaff = staffRepository.findById(id).map(staff -> {
            staff.setFirstName(newStaff.getFirstName());
            staff.setLastName(newStaff.getLastName());
            staff.setEmail(newStaff.getEmail());
            staff.setPhone(newStaff.getPhone());
            staff.setPassword(newStaff.getPassword());
            return staffRepository.save(staff);
        }).orElseGet(() -> {
            newStaff.setId(id);
            return staffRepository.save(newStaff);
        });
        return updatedStaff;
    }
    public boolean delete(Long id) {
        boolean exists = staffRepository.existsById(id);
        if(exists) {
            staffRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
