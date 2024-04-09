package com.example.HotelManagement.service.Hotel;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.HotelManagement.specifications.HotelSpecification.hasName;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> findAll(){
        return hotelRepository.findAll();
    }
    public Hotel save(Hotel newHotel){
        return hotelRepository.save(newHotel);
    }
    public Optional<Hotel> findById(Long id){
        return hotelRepository.findById(id);
    }

    public Optional<Hotel> findByName(String name) {
        return hotelRepository.findOne(hasName(name)).stream().findFirst();
    }
    public boolean findDuplicateHotelName(Hotel hotel){
        List<Hotel> foundHotel = hotelRepository.findByName(hotel.getName().trim());
        return !foundHotel.isEmpty();
    }
    public Hotel update(Hotel newHotel, Long id){
        Hotel updatedHotel = hotelRepository.findById(id).map(hotel -> {
            hotel.setName(newHotel.getName());
            hotel.setPhone(newHotel.getPhone());
            hotel.setAddress(newHotel.getAddress());
            return hotelRepository.save(hotel);
        }).orElseGet(() -> {
            newHotel.setId(id);
            return hotelRepository.save(newHotel);
        });
        return updatedHotel;
    }
    public boolean delete(Long id) {
        boolean exists = hotelRepository.existsById(id);
        if(exists) {
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
