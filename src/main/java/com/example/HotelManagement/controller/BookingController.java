package com.example.HotelManagement.controller;

import com.example.HotelManagement.dto.BookingDTO;
import com.example.HotelManagement.dto.guestBookingDTO;
import com.example.HotelManagement.dto.guestEmailDTO;
import com.example.HotelManagement.dto.statusRequest;
import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.service.Booking.BookingService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Get a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Booking> foundResource = bookingService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find book with id = " + id, "")
            );
        }
    }
    @Operation(summary = "Get a book by user email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = guestEmailDTO.class)), }),
            @ApiResponse(responseCode = "400", description = "Please input your email"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/findByEmail")
    public ResponseEntity<ResponseObject> findByUserEmail(@RequestBody guestEmailDTO email) {
        List<Booking> foundResource = bookingService.findByUserEmail(email.getEmail());
        if(!foundResource.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find book with user email = " + email.getEmail(), "")
            );
        }
    }
    @Operation(summary = "Gets all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find")
    public ResponseEntity<List<Booking>> findAll() {
        List<Booking> bookingList = bookingService.findAll();
        if(bookingList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @Operation(summary = "Get book by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)),}),
            @ApiResponse(responseCode = "400", description = "Please input"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByStatus")
    public ResponseEntity<ResponseObject> findByStatus(@RequestParam("status") String statusString) {
        List<Booking> foundResource = bookingService.findByStatus(statusString);
        if (!foundResource.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "An error occured - Please check your input", "")
            );
        }
    }

    @Operation(summary = "Get book by guest name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)),}),
            @ApiResponse(responseCode = "400", description = "Please input"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findBookByName")
    public ResponseEntity<ResponseObject> findByGuestName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<Booking> foundResource = bookingService.findByGuestName(firstName, lastName);
        if (!foundResource.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "No data founded", "")
            );
        }
    }
    @Operation(summary = "Book a room", description = """
            Booking status:\s
                booked,
                paid,
                checkedIn,
                cancelled,
                checkedOut""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully inserted resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = guestBookingDTO.class)), }),
            @ApiResponse(responseCode = "406", description = "The room is unavailable",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) }),
            @ApiResponse(responseCode = "500", description = "Cannot book this room",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PostMapping("/book")
    public ResponseEntity<ResponseObject> BookingRoom(@RequestBody guestBookingDTO newBooking) {
        try {
            Booking book = bookingService.save(newBooking);
            if (book == null){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("invalid", "The room is unavailable", "")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", book)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "Cannot book this room", "")
            );
        }
    }
    @Operation(summary = "Replace a booking resource in the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request - Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody BookingDTO newBooking, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", bookingService.update(newBooking, id))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }
    @Operation(summary = "Update a book status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "406", description = "Invalid Request - Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) }),
            @ApiResponse(responseCode = "500", description = "An error occurred while updating",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PatchMapping("/update/status/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@RequestBody statusRequest status, @PathVariable Long id) {
        try {
            Booking updatedBook = bookingService.updateBookStatus(id, status.getStatus());
            if (updatedBook==null){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("invalid", "Status is invalid", "")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", updatedBook)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating", "")
            );
        }
    }
    @Operation(summary = "Delete a booking resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "404", description = "Data not exists",
                    content = { @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = bookingService.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find data to delete", "")
        );
    }

    @Scheduled(cron = "0 0 0 * * ?") // Chạy vào lúc 00:00:00 mỗi ngày
    @Hidden
    public void deleteByDateAndStatus() {
        bookingService.deleteBookingsByStatus();
    }
}
