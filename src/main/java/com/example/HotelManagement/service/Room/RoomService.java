package com.example.HotelManagement.service.Room;

import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.dto.roomDTO;
import com.example.HotelManagement.model.enums.RoomStatus;
import com.example.HotelManagement.repository.RoomRepository;
import com.example.HotelManagement.service.Hotel.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private TypeService typeService;

    @Cacheable(value = "roomList")
    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room save(roomDTO newRoom){
        Room room = new Room();
        try {
            RoomStatus status = RoomStatus.valueOf(newRoom.getStatus());
            room.setStatus(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
        hotelService.findById(newRoom.getHotel_id()).ifPresent(room::setHotel);
        typeService.findById(newRoom.getType_id()).ifPresent(room::setType);
        return roomRepository.save(room);
    }

    public Optional<Room> findById(Long id){
        return roomRepository.findById(id);
    }

    @Cacheable(value = "roomList")
    public List<Room> findByRoomType(String roomType){
        return roomRepository.findRoomByType(roomType);
    }

    public Room update(roomDTO newRoom, Long id) {
        return roomRepository.findById(id)
                .map(room -> {
                    try {
                        RoomStatus status = RoomStatus.valueOf(newRoom.getStatus());
                        room.setStatus(status);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                    hotelService.findById(newRoom.getHotel_id()).ifPresent(room::setHotel);
                    typeService.findById(newRoom.getType_id()).ifPresent(room::setType);
                    return roomRepository.save(room);
                })
                .orElseGet(() -> {
                    Room room = new Room();
                    try {
                        RoomStatus status = RoomStatus.valueOf(newRoom.getStatus());
                        room.setStatus(status);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                    hotelService.findById(newRoom.getHotel_id()).ifPresent(room::setHotel);
                    typeService.findById(newRoom.getType_id()).ifPresent(room::setType);
                    return roomRepository.save(room);
                });
    }
    public Room updateRoomStatus(Long id, String statusString){
        Room updatedRoom = roomRepository.getRoomById(id);
        for (RoomStatus status : RoomStatus.values()){
            if (status.name().equalsIgnoreCase(statusString)){
                updatedRoom.setStatus(status);
                return roomRepository.save(updatedRoom);
            }
        }
        return null;
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
