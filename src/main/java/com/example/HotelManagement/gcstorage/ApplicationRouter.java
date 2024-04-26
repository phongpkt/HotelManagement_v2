package com.example.HotelManagement.gcstorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationRouter {
    @Autowired
    private StorageService storageService;

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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String uploadedFileName = storageService.uploadFile(file, "test");
            return ResponseEntity.ok("File uploaded successfully to folder: " + uploadedFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}