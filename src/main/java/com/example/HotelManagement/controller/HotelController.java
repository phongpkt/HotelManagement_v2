package com.example.HotelManagement.controller;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.exceptions.ResponseObject;
import com.example.HotelManagement.service.Hotel.HotelService;
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
@RequestMapping("/api/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Gets Hotel by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)), }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Hotel> foundResource = hotelService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find hotel with id = " + id, "")
            );
        }
    }
    @Operation(summary = "Gets all Hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find")
    public ResponseEntity<List<Hotel>> findAll() {
        List<Hotel> hotelList = hotelService.findAll();
        if(hotelList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }

    @Operation(summary = "Gets Hotel by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByName")
    public ResponseEntity<ResponseObject> findByName(@RequestParam("name") String name) {
        Optional<Hotel> foundResource = hotelService.findByName(name);
        if (foundResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find hotel with name = " + name, "")
            );
        }
    }
    @Operation(summary = "Insert hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully inserted resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody Hotel newHotel) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", hotelService.save(newHotel))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while saving", "")
            );
        }
    }
    @Operation(summary = "Replace a hotel resource in the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody Hotel newHotel, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", hotelService.update(newHotel, id))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }
    @Operation(summary = "Delete a hotel resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)), }),
            @ApiResponse(responseCode = "404", description = "Hotel not exists",
                    content = { @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = hotelService.delete(id);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find hotel to delete", "")
        );
    }
}
