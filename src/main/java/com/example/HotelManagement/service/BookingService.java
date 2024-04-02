package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.model.dto.bookingDTO;
import com.example.HotelManagement.model.enums.BookingStatus;
import com.example.HotelManagement.model.enums.RoomStatus;
import com.example.HotelManagement.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomService roomService;

    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
    public Booking save(bookingDTO newBooking){
        Booking book = new Booking();
        book.setCheckInDate(newBooking.getCheckInDate());
        book.setCheckOutDate(newBooking.getCheckOutDate());
        // Chuyển đổi từ Date sang LocalDate
        LocalDate checkInDate = newBooking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOutDate = newBooking.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //room
        Optional<Room> room = roomService.findById(newBooking.getRoom());
        if (room.isPresent()){
            if(room.get().getStatus() == RoomStatus.available){
                book.setRoom(room.get());
                RoomType type = room.get().getType();
                //price
                long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                double total_price = numberOfDays * type.getPricePerNight();
                book.setTotalPrice(total_price);
            }
            room.get().setStatus(RoomStatus.reserved);
        }
        return bookingRepository.save(book);
    }
    public Optional<Booking> findById(Long id){
        return bookingRepository.findById(id);
    }
    public Booking update(Booking newBooking, Long id){
        Booking updatedBook = bookingRepository.findById(id).map(book -> {
            book.setCheckInDate(newBooking.getCheckInDate());
            book.setCheckOutDate(newBooking.getCheckOutDate());
            book.setTotalPrice(newBooking.getTotalPrice());
            return bookingRepository.save(book);
        }).orElseGet(() -> {
            newBooking.setId(id);
            return bookingRepository.save(newBooking);
        });
        return updatedBook;
    }
    public boolean delete(Long id) {
        boolean exists = bookingRepository.existsById(id);
        if(exists) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
