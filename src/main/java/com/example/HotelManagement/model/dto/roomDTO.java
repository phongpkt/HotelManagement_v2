package com.example.HotelManagement.model.dto;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.model.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class roomDTO {
    private long hotel_id;
    private long type_id;
    private String Status;

}
