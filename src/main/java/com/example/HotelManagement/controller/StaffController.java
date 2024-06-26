package com.example.HotelManagement.controller;

import com.example.HotelManagement.dto.updateStaffDTO;
import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.service.Staff.StaffService;
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
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Operation(summary = "Gets staff by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)), }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Staff> foundResource = staffService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find staff with id = " + id, "")
            );
        }
    }
    @Operation(summary = "Gets all Staff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find")
    public ResponseEntity<List<Staff>> findAll() {
        List<Staff> staffList = staffService.findAll();
        if(staffList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @Operation(summary = "Gets staff by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/find/{firstName}/{lastName}")
    public ResponseEntity<ResponseObject> findByFirstnameAndLastname(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        Optional<Staff> foundResource = staffService.findByFirstnameAndLastname(firstName, lastName);
        if (foundResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find staff with name = " + firstName + " " + lastName, "")
            );
        }
    }

    @Operation(summary = "Gets staff by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByEmail")
    public ResponseEntity<ResponseObject> findByEmail(@RequestParam("email") String email) {
        Optional<Staff> foundResource = staffService.findByEmail(email);
        if (foundResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "This " + email + " does not exits", "")
            );
        }
    }

    @Operation(summary = "Gets staff by phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByPhone")
    public ResponseEntity<ResponseObject> findByPhone(@RequestParam("phone") String phone) {
        Optional<Staff> foundResource = staffService.findByPhone(phone);
        if (foundResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "This person " + phone + " does not exits", "")
            );
        }
    }

    @Operation(summary = "Gets staff by role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByRole")
    public ResponseEntity<ResponseObject> findByStaffRole(@RequestParam("role") String role) {
        List<Staff> foundResource = staffService.findByStaffRole(role);
        if (!foundResource.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "No data found", "")
            );
        }
    }

    @Operation(summary = "Gets staff by hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByHotel")
    public ResponseEntity<ResponseObject> findByHotel(@RequestParam("hotel") Integer hotel) {
        List<Staff> foundResource = staffService.findByHotel(Long.valueOf(hotel));
        if (!foundResource.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "An error occurred - Please check your input", "")
            );
        }
    }
    @Operation(summary = "Create a new staff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully inserted resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)), }),
            @ApiResponse(responseCode = "406", description = "Staff email already exists"),
            @ApiResponse(responseCode = "500", description = "Invalid Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody Staff newStaff) {
        boolean isDuplicated = staffService.findDuplication(newStaff);
        if (!isDuplicated){
            try {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "successfully", staffService.save(newStaff))
                );
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new ResponseObject("error", "Please check your input - Maybe the hotel doesn't exist", "")
                );
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("ok", "This email has already exists", "!!! -> " + newStaff.getEmail())
        );
    }
    @Operation(summary = "Replace a staff resource in the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)), }),
            @ApiResponse(responseCode = "500", description = "Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody updateStaffDTO newStaff, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", staffService.update(newStaff, id))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }
    @Operation(summary = "Delete a staff resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Staff.class)), }),
            @ApiResponse(responseCode = "404", description = "Staff not exists",
                    content = { @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = staffService.delete(id);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find staff to delete", "")
        );
    }
}
