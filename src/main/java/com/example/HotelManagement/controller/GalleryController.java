package com.example.HotelManagement.controller;

import com.example.HotelManagement.exceptions.ResponseObject;
import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin("http://localhost:5173/")
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
//    public ResponseEntity<List<Gallery>> findAll() throws IOException {
//        List<Gallery> imagesList = galleryService.getAllImages();
//        if (imagesList.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(imagesList, HttpStatus.OK);
//    }
    //TODO: FIX load all images
    public ResponseEntity<List<byte[]>> getAllImages() throws IOException {
        List<Gallery> imagesList = galleryService.getAllImages();
        List<byte[]> imageBytesList = new ArrayList<>();
        List<String> imageFormatList  = new ArrayList<>();
        for (Gallery image : imagesList) {
            if (image != null) {
                String imageURL = image.getImage_url();
                byte[] imageBytes = galleryService.getImage(imageURL);
                imageBytesList.add(imageBytes);
                imageFormatList.add(image.getImage_format());
            }
        }
        if (!imageBytesList.isEmpty()) {
            List<MediaType> mediaTypes = new ArrayList<>();
            for (String format : imageFormatList) {
                mediaTypes.add(MediaType.valueOf(format));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(imageBytesList);
        }
        return null;
    }
}
