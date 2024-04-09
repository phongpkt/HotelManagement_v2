package com.example.HotelManagement.service.Room;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.model.dto.roomDTO;
import com.example.HotelManagement.model.enums.RoomStatus;
import com.example.HotelManagement.repository.RoomRepository;
import com.example.HotelManagement.service.Hotel.HotelService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.HotelManagement.specifications.RoomSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private EntityManager entityManager;

    @Cacheable(value = "roomList")
    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Cacheable(value = "roomList")
    public List<Room> findByRoomType(String roomType) {
        return roomRepository.findRoomByType(roomType);
    }

    public List<Room> findByRoomStatusCriteria(String status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> roomRoot = cq.from(Room.class);
        Predicate statusPredicate = cb.equal(roomRoot.get("Status"), status);
        cq.where(statusPredicate);
        TypedQuery<Room> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public List<Room> findByRoomStatusSpecification(String statusString) {
        RoomStatus status;
        try {
            status = RoomStatus.valueOf(statusString);
        } catch (IllegalArgumentException ex) {
            return Collections.emptyList();
        }
        return roomRepository.findAll(hasStatus(status));
    }

    public List<Room> findByHotelAndType(Long hotel_id, Long type_id) {
        Optional<Hotel> optionalHotel = hotelService.findById(hotel_id);
        Optional<RoomType> optionalRoomType = typeService.findById(type_id);
        if (optionalHotel.isPresent() && optionalRoomType.isPresent()) {
            return roomRepository.findAll(where(hasHotel(optionalHotel.get())).and(hasType(optionalRoomType.get())));
        } else {
            return Collections.emptyList();
        }
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
