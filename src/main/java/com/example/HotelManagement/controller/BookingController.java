package com.example.HotelManagement.controller;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.exceptions.ResponseObject;
import com.example.HotelManagement.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Gets data by ID")
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
    @Operation(summary = "Gets all book")
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
    @Operation(summary = "Insert new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully inserted resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request - Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody Booking newBooking) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", bookingService.save(newBooking))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while saving", "")
            );
        }
    }
    @Operation(summary = "Replace a booking resource in the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request - Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody Booking newBooking, @PathVariable Long id) {
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
    @Operation(summary = "Delete a booking resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)), }),
            @ApiResponse(responseCode = "404", description = "Data not exists",
                    content = { @Content(mediaType = "application/json")})
    })
    @PostMapping("/delete/{id}")
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
}
