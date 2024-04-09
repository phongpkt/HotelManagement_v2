package com.example.HotelManagement.repository;

import com.example.HotelManagement.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<RoomType, Long>, JpaSpecificationExecutor<RoomType> {
    List<RoomType> findByName(String name);
}
