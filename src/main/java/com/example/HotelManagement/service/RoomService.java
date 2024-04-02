package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.model.dto.roomDTO;
import com.example.HotelManagement.model.enums.RoomStatus;
import com.example.HotelManagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<Room> findAll(){
        return roomRepository.findAll();
    }
    public Room save(roomDTO newRoom){
        Room room = new Room();
        room.setStatus(newRoom.getStatus());
        //hotel
        Optional<Hotel> hotel = hotelService.findById(newRoom.getHotel_id());
        hotel.ifPresent(room::setHotel);
        //room_type
        Optional<RoomType> roomType = typeService.findById(newRoom.getType_id());
        roomType.ifPresent(room::setType);
        return roomRepository.save(room);
    }
    public Optional<Room> findById(Long id){
        return roomRepository.findById(id);
    }
    public Room update(roomDTO newRoom, Long id) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setStatus(newRoom.getStatus());
                    hotelService.findById(newRoom.getHotel_id()).ifPresent(room::setHotel);
                    typeService.findById(newRoom.getType_id()).ifPresent(room::setType);
                    return roomRepository.save(room);
                })
                .orElseGet(() -> {
                    Room room = new Room();
                    room.setStatus(newRoom.getStatus());
                    hotelService.findById(newRoom.getHotel_id()).ifPresent(room::setHotel);
                    typeService.findById(newRoom.getType_id()).ifPresent(room::setType);
                    return roomRepository.save(room);
                });
    }
    public Room updateRoomStatus(Long id, String status){
        Room updatedRoom = roomRepository.getRoomById(id);
        updatedRoom.setStatus(RoomStatus.valueOf(status));
        return roomRepository.save(updatedRoom);
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
