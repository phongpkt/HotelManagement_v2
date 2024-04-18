package com.example.HotelManagement.repository;

import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
