package com.example.HotelManagement.repository;

import com.example.HotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    Room getRoomById(Long id);
    @Query("SELECT r FROM Room r JOIN r.type rt WHERE rt.name = :roomType")
    List<Room> findRoomByType(String roomType);
}
