package com.example.HotelManagement.gcstorage;

import com.example.HotelManagement.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationRouter {
    @Autowired
    private StorageService storageService;
    @Autowired
    private GalleryService galleryService;

    @GetMapping("/{directory}/{fileName}") //directory: test, images
    public ResponseEntity<byte[]> getFile(@PathVariable("directory") String directory, @PathVariable("fileName") String fileName) {
        try {
            byte[] fileContent = storageService.getFile(directory, fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/images")
    public List<String> getAllImages() {
        return storageService.getImagePaths();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("type") String typeString) {
        try {
            // Upload file to GCS
            String imageUrl = storageService.uploadFile(file);

            // Save file info to database
            String imageFormat = file.getContentType();
            galleryService.saveImage(typeString, imageUrl, imageFormat);

            return ResponseEntity.ok("File uploaded successfully and info saved to DB");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        Boolean message = storageService.deleteFile(decodedFileName);
        if (message){
            galleryService.deleteByFileName(decodedFileName);
            return ResponseEntity.ok("Delete successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete");

    }
}