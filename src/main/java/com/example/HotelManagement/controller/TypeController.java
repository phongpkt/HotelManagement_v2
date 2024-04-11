package com.example.HotelManagement.controller;

import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.service.Room.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roomType")
@CrossOrigin("http://localhost:5173/")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @Operation(summary = "Get a room type by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)), }),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<RoomType> foundResource = typeService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find type with id = " + id, "")
            );
        }
    }
    @Operation(summary = "Gets all room type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/find")
    public ResponseEntity<List<RoomType>> findAll() {
        List<RoomType> typeList = typeService.findAll();
        if (typeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeList, HttpStatus.OK);
    }

    @Operation(summary = "Find hotel by room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class)),}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findHotel/{id}")
    public ResponseEntity<ResponseObject> findHotelByRoom(@PathVariable("id") Long id) {
        Hotel hotel = typeService.findHotelByRoom(id);
        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "This " + hotel + " does not exits", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Successfully retrieved data", hotel)
        );
    }

    @Operation(summary = "Gets room type by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)),}),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByName")
    public ResponseEntity<ResponseObject> findByName(@RequestParam("name") String name) {
        Optional<RoomType> foundResource = typeService.findByName(name);
        if (foundResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "This " + name + " does not exits", "")
            );
        }
    }

    @Operation(summary = "Gets all room type with price lesser than")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)),}),
            @ApiResponse(responseCode = "204", description = "No data"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByPrice")
    public ResponseEntity<List<RoomType>> findByPriceLessThan(@RequestParam("price") Double price) {
        List<RoomType> typeList = typeService.findByPriceLessThan(price);
        if (typeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeList, HttpStatus.OK);
    }

    @Operation(summary = "Gets all room type with capacity greater than")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)),}),
            @ApiResponse(responseCode = "204", description = "No Data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)),}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/findByCapacity")
    public ResponseEntity<List<RoomType>> findByCapacityGreaterThan(@RequestParam("capacity") Integer capacity) {
        List<RoomType> typeList = typeService.findByCapacityGreaterThan(capacity);
        if(typeList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeList, HttpStatus.OK);
    }
    @Operation(summary = "Insert room type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully inserted resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)), }),
            @ApiResponse(responseCode = "500", description = "Invalid Request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody RoomType newType) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", typeService.save(newType))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while saving", "")
            );
        }
    }
    @Operation(summary = "Replace a room type in the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)), }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class)) })
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody RoomType newType, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", typeService.update(newType, id))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }

    @Operation(summary = "Update a room type image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)),}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class))})
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePreviewImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long id) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String format = multipartFile.getContentType();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", typeService.updatePreviewImage(id, fileName, format, multipartFile))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }

    @Operation(summary = "Save a room type image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)),}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseObject.class))})
    })
    @PostMapping("/save/image/{id}")
    public ResponseEntity<ResponseObject> saveRoomTypeImage(@RequestParam("image") MultipartFile multipartFile, @PathVariable Long id) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String format = multipartFile.getContentType();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", typeService.saveRoomTypeImages(id, fileName, format, multipartFile))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }

    @GetMapping("/getImage/{id}")
    public ResponseEntity<?> getImages(@PathVariable Long id) throws IOException {
        Optional<RoomType> roomType = typeService.findById(id);
        if (roomType.isPresent()) {
            if (roomType.get().getPreviewImage() != null) {
                String imageURL = roomType.get().getPreviewImage().getImage_url();
                String imageFormat = roomType.get().getPreviewImage().getImage_format();
                byte[] imageBytes = typeService.getImage(imageURL);
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(imageFormat))
                        .body(imageBytes);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("error", "No data found", "")
        );
    }
    @Operation(summary = "Delete a room type resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomType.class)), }),
            @ApiResponse(responseCode = "404", description = "Room type not exists",
                    content = { @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = typeService.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find room type to delete", "")
        );
    }
}
