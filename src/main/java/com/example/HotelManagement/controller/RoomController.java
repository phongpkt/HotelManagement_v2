package com.example.HotelManagement.controller;

import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.dto.roomDTO;
import com.example.HotelManagement.model.dto.roomTypeDTO;
import com.example.HotelManagement.model.dto.statusRequest;
import com.example.HotelManagement.model.exceptions.ResponseObject;
import com.example.HotelManagement.service.Room.RoomService;
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
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Operation(summary = "Gets all room type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<Room>> findAll() {
        List<Room> roomList = roomService.findAll();
        if(roomList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }
    @Operation(summary = "Gets room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)), }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Room> foundResource = roomService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find room with id = " + id, "")
            );
        }
    }
    @Operation(summary = "Gets room by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)), }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/findByType")
    public ResponseEntity<ResponseObject> findByRoomType(@RequestBody roomTypeDTO roomType) {
        List<Room> foundResource = roomService.findByRoomType(roomType.getRoomType());
        if(!foundResource.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find room with type = " + roomType.getRoomType(), "")
            );
        }
    }
    @Operation(summary = "Insert room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully inserted resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = roomDTO.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request - Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody roomDTO newRoom) {
        try {
            Room newRoomData = roomService.save(newRoom);
            if(newRoomData!=null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "successfully", newRoomData)
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("ok", "Invalid request - Please check your input", "")
                );
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while saving", "")
            );
        }
    }

    @Operation(summary = "Replace a room resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request - Please check your input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody roomDTO newRoom, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", roomService.update(newRoom, id))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }
    @Operation(summary = "Update a room status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)), }),
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
            Room updatedRoom = roomService.updateRoomStatus(id, status.getStatus());
            if (updatedRoom==null){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("invalid", "Status is invalid", "")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", updatedRoom)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating", "")
            );
        }
    }

    @Operation(summary = "Delete a room resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Room.class)), }),
            @ApiResponse(responseCode = "404", description = "Room not exists",
                    content = { @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = roomService.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find room to delete", "")
        );
    }
    @GetMapping("/find/test1/{status}")
    public ResponseEntity<List<Room>> findByStatusCriteriaAPI(@PathVariable("status") String status) {
        List<Room> roomList = roomService.findByRoomStatusCriteria(status);
        if (roomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @GetMapping("/findByStatus")
    public ResponseEntity<List<Room>> findByStatus(@RequestParam("status") String status) {
        List<Room> roomList = roomService.findByRoomStatusSpecification(status);
        if (roomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Room>> findByHotelAndType(@RequestParam("hotel") Integer hotelId, @RequestParam("type") Integer typeId) {
        List<Room> roomList = roomService.findByHotelAndType(Long.valueOf(hotelId), Long.valueOf(typeId));
        if (roomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }
}
