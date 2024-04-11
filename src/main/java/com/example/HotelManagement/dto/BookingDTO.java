package com.example.HotelManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class BookingDTO {
    private long room;
    private Date checkInDate;
    private Date checkOutDate;
    private String status;
}
