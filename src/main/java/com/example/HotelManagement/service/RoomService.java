package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll(){
        return roomRepository.findAll();
    }
    public Room save(Room newRoom){
        return roomRepository.save(newRoom);
    }
    public Optional<Room> findById(Long id){
        return roomRepository.findById(id);
    }
    public Room update(Room newRoom, Long id){
        Room updatedRoom = roomRepository.findById(id).map(room -> {
            room.setStatus(newRoom.getStatus());
            return roomRepository.save(room);
        }).orElseGet(() -> {
            newRoom.setId(id);
            return roomRepository.save(newRoom);
        });
        return updatedRoom;
    }
    public boolean delete(Long id) {
        boolean exists = roomRepository.existsById(id);
        if(exists) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
