package com.example.HotelManagement.service.Booking;

import com.example.HotelManagement.model.Booking;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(String to, Booking booking) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("phamkhacthanhphong@gmail.com");
        msg.setTo(to);
        msg.setSubject("Booking Details");

        String body = "Hello,\n\n" +
                "Thank you for booking with us.\n" +
                "Please check the information below.\n\n" +
                "Booking Details:\n" +
                "Check-In Date: " + formatDate(booking.getCheckInDate()) + "\n" +
                "Check-Out Date: " + formatDate(booking.getCheckInDate()) + "\n" +
                "Total Price: " + booking.getTotalPrice() + "\n\n" +
                "Guest Information:\n" +
                "Name: " + booking.getGuest().getFirstName() + " " + booking.getGuest().getLastName() + "\n" +
                "Phone: " + booking.getGuest().getPhone() + "\n" +
                "Email: " + booking.getGuest().getEmail() + "\n" +
                "Address: " + booking.getGuest().getAddress() + "\n" +
                "Date of Birth: " + formatDate(booking.getGuest().getDate_of_birth()) + "\n\n" +
                "Room Information:\n" +
                "Hotel Name: " + booking.getRoom().getHotel().getName() + "\n" +
                "Hotel Address: " + booking.getRoom().getHotel().getAddress() + "\n" +
                "Hotel Phone: " + booking.getRoom().getHotel().getPhone() + "\n" +
                "Room Type: " + booking.getRoom().getType().getName() + "\n" +
                "Room Description: " + booking.getRoom().getType().getDescription() + "\n";

        msg.setText(body);

        mailSender.send(msg);
    }

    private String formatDate(Date date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inputDate = inputFormat.parse(String.valueOf(date));
            return outputFormat.format(inputDate);
        } catch (ParseException e) {
            return ("Error parsing date: " + e.getMessage());
        }
    }
}
