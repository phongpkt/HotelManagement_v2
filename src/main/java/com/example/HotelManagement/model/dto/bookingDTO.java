package com.example.HotelManagement.model.dto;

import com.example.HotelManagement.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class bookingDTO {
    private long room;
    private Date checkInDate;
    private Date checkOutDate;
    private BookingStatus status;
}
