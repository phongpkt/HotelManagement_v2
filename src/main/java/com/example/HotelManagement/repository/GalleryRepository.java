package com.example.HotelManagement.repository;

import com.example.HotelManagement.enums.ImageTypes;
import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT g FROM Gallery g WHERE g.image_type = :image_type")
    List<Gallery> findByImageType(@Param("image_type") ImageTypes image_type);

    @Transactional
    @Modifying
    @Query("DELETE FROM Gallery g WHERE g.image_url = :imageUrl")
    void deleteByImageUrl(@Param("imageUrl") String imageUrl);
}
