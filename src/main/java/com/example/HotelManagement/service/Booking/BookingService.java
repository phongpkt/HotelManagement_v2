package com.example.HotelManagement.service.Booking;

import com.example.HotelManagement.dto.BookingDTO;
import com.example.HotelManagement.dto.guestBookingDTO;
import com.example.HotelManagement.enums.BookingStatus;
import com.example.HotelManagement.enums.RoomStatus;
import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Guest;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.repository.BookingRepository;
import com.example.HotelManagement.repository.GuestRepository;
import com.example.HotelManagement.service.Room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.HotelManagement.specifications.BookingSpecification.hasGuest;
import static com.example.HotelManagement.specifications.BookingSpecification.hasStatus;
import static com.example.HotelManagement.specifications.GuestSpecification.hasFirstName;
import static com.example.HotelManagement.specifications.GuestSpecification.hasLastName;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private EmailService emailService;


    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
    public Optional<Booking> findById(Long id){
        return bookingRepository.findById(id);
    }

    public List<Booking> findByUserEmail(String email) {
        return bookingRepository.findBookingByUserEmail(email);
    }

    public List<Booking> findByStatus(String statusString) {
        BookingStatus status = getStatus(statusString);
        return bookingRepository.findAll(hasStatus(status));
    }

    public List<Booking> findByGuestName(String firstName, String lastName) {
        List<Guest> guests = guestRepository.findAll(where(hasFirstName(firstName)).and(hasLastName(lastName)));
        List<Booking> bookings = new ArrayList<>();
        for (Guest guest : guests) {
            List<Booking> guestBookings = bookingRepository.findAll(hasGuest(guest));
            bookings.addAll(guestBookings);
        }
        return bookings;
    }

    public Booking save(guestBookingDTO newBooking){
        Optional<Room> room = roomService.findById(newBooking.getRoom());
        if (room.isPresent()){
            Booking book = new Booking();
            book.setCheckInDate(newBooking.getCheckInDate());
            book.setCheckOutDate(newBooking.getCheckOutDate());
            book.setRoom(room.get());
            book.setTotalPrice(newBooking.getTotalPrice());
            book.setStatus(BookingStatus.booked);
            Guest guest = getGuest(newBooking);
            book.setGuest(guest);

            guestRepository.save(guest);
//            emailService.sendEmailToGuest(newBooking.getEmail(), book); //Guest
//            emailService.sendEmailToHost(book); //Host
            return bookingRepository.save(book);
        }
        return null;
    }
    public Booking update(BookingDTO newBooking, Long id){
        Booking updatedBook = bookingRepository.getBookingById(id);
        updatedBook.setCheckInDate(newBooking.getCheckInDate());
        updatedBook.setCheckOutDate(newBooking.getCheckOutDate());
        updatedBook.setTotalPrice(newBooking.getTotalPrice());
        for (BookingStatus status : BookingStatus.values()){
            if (status.name().equalsIgnoreCase(newBooking.getStatus())){
                updatedBook.setStatus(status);
                return bookingRepository.save(updatedBook);
            }
        }
        //previous room
        Room room = updatedBook.getRoom();
        if (room.getStatus() == RoomStatus.reserved) {
            room.setStatus(RoomStatus.available);
        }
        //update room
        Optional<Room> update_room = roomService.findById(newBooking.getRoom());
        if (update_room.isPresent()) {
            if (update_room.get().getStatus() == RoomStatus.available) {
                updatedBook.setRoom(update_room.get());
                RoomType type = update_room.get().getType();
            }
            update_room.get().setStatus(RoomStatus.reserved);
        }

        return bookingRepository.save(updatedBook);
    }

    public boolean delete(Long id) {
        boolean exists = bookingRepository.existsById(id);
        if(exists) {
            guestRepository.deleteById(id);
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteBookingsByStatus() {
        List<Booking> bookings = bookingRepository.findByStatus();
        for (Booking booking : bookings) {
            bookingRepository.delete(booking);
        }
    }


    //======================================================================================
    private BookingStatus getStatus(String status){
        try {
            return BookingStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    private Guest getGuest(guestBookingDTO newBooking){
        Guest guest = new Guest();
        guest.setFullName(newBooking.getFullName());
        guest.setPhone(newBooking.getPhone());
        guest.setEmail(newBooking.getEmail());
        return guest;
    }
}
