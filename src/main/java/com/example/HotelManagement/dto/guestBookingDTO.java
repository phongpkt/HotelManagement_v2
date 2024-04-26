package com.example.HotelManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class guestBookingDTO {
    private long room;
    private String checkInDate;
    private String checkOutDate;
    private String totalPrice;
    private String fullName;
    private String phone;
    private String email;
}
