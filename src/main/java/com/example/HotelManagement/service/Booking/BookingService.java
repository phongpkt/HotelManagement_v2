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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
            if(room.get().getStatus() == RoomStatus.available){
                Booking book = new Booking();
                book.setCheckInDate(newBooking.getCheckInDate());
                book.setCheckOutDate(newBooking.getCheckOutDate());
                book.setRoom(room.get());
                RoomType type = room.get().getType();
                book.setTotalPrice(calculate_Price(type, newBooking.getCheckInDate(), newBooking.getCheckOutDate()));
                book.setStatus(BookingStatus.booked);
                room.get().setStatus(RoomStatus.reserved);
                Guest guest = getGuest(newBooking);
                book.setGuest(guest);
                guestRepository.save(guest);
                return bookingRepository.save(book);
            }
        }
        return null;
    }
    public Booking update(BookingDTO newBooking, Long id){
        Booking updatedBook = bookingRepository.getBookingById(id);
            updatedBook.setCheckInDate(newBooking.getCheckInDate());
            updatedBook.setCheckOutDate(newBooking.getCheckOutDate());
            //previous room
            Room room = updatedBook.getRoom();
                if (room.getStatus() == RoomStatus.reserved){
                    room.setStatus(RoomStatus.available);
                }
            //update room
            Optional<Room> update_room = roomService.findById(newBooking.getRoom());
                if (update_room.isPresent()){
                    if(update_room.get().getStatus() == RoomStatus.available){
                        updatedBook.setRoom(update_room.get());
                        RoomType type = update_room.get().getType();
                        updatedBook.setTotalPrice(calculate_Price(type, newBooking.getCheckInDate(), newBooking.getCheckOutDate()));
                    }
                    update_room.get().setStatus(RoomStatus.reserved);
                }
        return bookingRepository.save(updatedBook);
    }
    public Booking updateBookStatus(Long id, String statusString){
        Booking updatedBook = bookingRepository.getBookingById(id);
        for (BookingStatus status : BookingStatus.values()){
            if (status.name().equalsIgnoreCase(statusString)){
                updatedBook.setStatus(status);
                return bookingRepository.save(updatedBook);
            }
        }
        return null;
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
    public void deleteBookingsByCheckOutDateAndStatus() {
        List<Booking> bookings = bookingRepository.findByCheckOutDateBeforeAndStatus();
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
    private double calculate_Price(RoomType room, Date checkInDate, Date checkOutDate){
        LocalDate localCheckInDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localCheckOutDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long numberOfDays = ChronoUnit.DAYS.between(localCheckInDate, localCheckOutDate);
        return numberOfDays * room.getPricePerNight();
    }
    private Guest getGuest(guestBookingDTO newBooking){
        Guest guest = new Guest();
        guest.setFirstName(newBooking.getFirstName());
        guest.setLastName(newBooking.getLastName());
        guest.setPhone(newBooking.getPhone());
        guest.setDate_of_birth(newBooking.getDate_of_birth());
        guest.setAddress(newBooking.getAddress());
        guest.setEmail(newBooking.getEmail());
        return guest;
    }
}
