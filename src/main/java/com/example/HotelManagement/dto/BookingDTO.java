package com.example.HotelManagement.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BookingDTO {
    private long room;
    private String checkInDate;
    private String checkOutDate;
    private String status;
    private String totalPrice;
}
