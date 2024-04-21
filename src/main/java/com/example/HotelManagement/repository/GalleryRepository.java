package com.example.HotelManagement.repository;

import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    @Query("""
        SELECT g FROM Gallery g
        JOIN g.roomType rt
        WHERE rt.id = :id
        """)
    List<Gallery> findByRoomType(Long id);
}
