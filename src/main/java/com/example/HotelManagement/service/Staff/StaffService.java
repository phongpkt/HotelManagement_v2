package com.example.HotelManagement.service.Staff;

import com.example.HotelManagement.dto.updateStaffDTO;
import com.example.HotelManagement.enums.StaffRole;
import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.repository.StaffRepository;
import com.example.HotelManagement.service.Hotel.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.HotelManagement.specifications.StaffSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private HotelService hotelService;

    public List<Staff> findAll(){
        return staffRepository.findAll();
    }

    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }
    public Optional<Staff> findByFirstnameAndLastname(String firstName, String lastName) {
        return staffRepository.findOne(where(hasFirstName(firstName).and(hasLastName(lastName))));
    }
    public Optional<Staff> findByEmail(String email) {
        return staffRepository.findOne(where(hasEmail(email)));
    }
    public Optional<Staff> findByPhone(String phone) {
        return staffRepository.findOne(where(hasPhone(phone)));
    }
    public List<Staff> findByStaffRole(String roleString) {
        StaffRole role;
        try {
            role = StaffRole.valueOf(roleString);
        } catch (IllegalArgumentException ex) {
            return Collections.emptyList();
        }
        return staffRepository.findAll(hasRole(role));
    }
    public List<Staff> findByHotel(Long hotel_id) {
        Optional<Hotel> optionalHotel = hotelService.findById(hotel_id);
        return optionalHotel.map(hotel -> staffRepository.findAll(where(hasHotel(hotel)))).orElse(Collections.emptyList());
    }

    public Staff save(Staff newStaff){
        return staffRepository.save(newStaff);
    }
    public boolean findDuplication(Staff staff){
        Optional<Staff> foundResource = staffRepository.findByEmail(staff.getEmail().trim());
        return foundResource.isPresent();
    }
    public Staff update(updateStaffDTO newStaff, Long id){
        Staff updatedStaff = staffRepository.findById(id)
                .map(staff -> {
                    // Update fields from newStaff DTO
                    staff.setFirstName(newStaff.getFirstName());
                    staff.setLastName(newStaff.getLastName());
                    staff.setEmail(newStaff.getEmail());
                    staff.setPhone(newStaff.getPhone());
                    return staffRepository.save(staff); // Save updated staff entity
                })
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
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
