package com.example.HotelManagement.controller;

import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.service.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @Operation(summary = "Get all gallery images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete resource",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gallery.class)),}),
            @ApiResponse(responseCode = "404", description = "Image not exists",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/find")
    public ResponseEntity<?> getImages() throws IOException {
        List<byte[]> images = galleryService.getAllImages();

        if (!images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(images);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("error", "No data found", "")
            );
        }
    }
}
