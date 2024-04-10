package com.example.HotelManagement.repository;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<RoomType, Long>, JpaSpecificationExecutor<RoomType> {
    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.rooms r JOIN r.type rt WHERE rt.id = :type_id")
    Hotel findHotelByRoom(Long type_id);
}
