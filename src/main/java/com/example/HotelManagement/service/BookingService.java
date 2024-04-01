package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
    public Booking save(Booking newBooking){
        return bookingRepository.save(newBooking);
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
