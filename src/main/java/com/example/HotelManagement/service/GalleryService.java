package com.example.HotelManagement.service;

import com.example.HotelManagement.enums.ImageTypes;
import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.repository.GalleryRepository;
import com.example.HotelManagement.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GalleryService {
    private final String root = "src/main/resources/static/images/";
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private TypeRepository typeRepository;

    public List<Gallery> getAllImages() throws IOException {
        return galleryRepository.findAll();
    }

    public Gallery save(String typeString, String fileName, MultipartFile multipartFile) throws IOException {
        Gallery image = new Gallery();
        Path uploadPath = Paths.get(root);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        image.setImage_url(uploadPath + "/" + fileName);
        for (ImageTypes type : ImageTypes.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                image.setImage_type(type);
            }
        }
        return galleryRepository.save(image);
    }

    public Gallery updatePreviewImageForRoom(RoomType roomType, String fileName, String format, MultipartFile multipartFile) throws IOException {
        Gallery image = new Gallery();
        Path uploadPath = Paths.get(root);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Gallery existingImage = roomType.getPreviewImage();
        if (existingImage != null) {
            roomType.setPreviewImage(null);
            String existingImagePath = existingImage.getImage_url();
            deleteImage(existingImage, existingImagePath); //delete existing image
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }

        image.setImage_url(uploadPath + "/" + fileName);
        image.setRoomType(roomType);
        image.setImage_format(format);
        image.setImage_type(ImageTypes.ACCOMMODATIONS); //default image_type for room

        return galleryRepository.save(image);
    }

    public void saveRoomTypeImages(RoomType roomType, String fileName, String format, MultipartFile multipartFile) throws IOException {
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
        image.setImage_format(format);
        image.setImage_type(ImageTypes.ACCOMMODATIONS); //default image_type for room
        galleryRepository.save(image);
    }

    public byte[] getImage(String imageURL) throws IOException {
        Path imagePath = Path.of(imageURL);
        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } else {
            return null; // Handle missing images
        }
    }

    public void deleteImage(Gallery existingImage, String imageDirectory) throws IOException {
        Path existingImagePath = Paths.get(imageDirectory);
        if (Files.exists(existingImagePath)) {
            Files.delete(existingImagePath);
            galleryRepository.delete(existingImage);
        }
    }
}
