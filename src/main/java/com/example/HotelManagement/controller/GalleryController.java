package com.example.HotelManagement.controller;

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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin("http://localhost:5173/")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @Operation(summary = "Get all gallery images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gallery.class)),}),
            @ApiResponse(responseCode = "204", description = "No data found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/find")
    public ResponseEntity<List<Gallery>> findAll() throws IOException {
        List<Gallery> imagesList = galleryService.getAllImages();
        if (imagesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imagesList, HttpStatus.OK);
    }

    @GetMapping("/getImage")
    public ResponseEntity<?> getImages(@RequestParam("url") String imageURL, @RequestParam("format") String format) throws IOException {
        byte[] imageBytes = galleryService.getImage(imageURL);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(format))
                    .body(imageBytes);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<List<Gallery>> findImagesByRoom(@PathVariable("id") Long id) throws IOException {
        List<Gallery> imagesList = galleryService.getImagesByRoom(id);
        if (imagesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imagesList, HttpStatus.OK);
    }
}
