package com.example.HotelManagement.service;

import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class GalleryService {
    private final String root = "src/main/resources/static/images/";
    @Autowired
    private GalleryRepository galleryRepository;

    public Gallery save(RoomType roomType, String fileName, MultipartFile multipartFile) throws IOException {
        Gallery image = new Gallery();
        Path uploadPath = Paths.get(root);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        image.setImage_url(uploadPath + "/" + fileName);
        image.setRoomType(roomType);
        return galleryRepository.save(image);
    }

    public Boolean deleteImage(Long id, String imageDirectory, String imageName) throws IOException {
        galleryRepository.deleteById(id);
        Path imagePath = Path.of(imageDirectory, imageName);
        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return true;
        } else {
            return false; // Handle missing images
        }
    }
}
