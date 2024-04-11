package com.example.HotelManagement.service;

import com.example.HotelManagement.enums.ImageTypes;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryService {
    private final String root = "src/main/resources/static/images/";
    @Autowired
    private GalleryRepository galleryRepository;

    public List<byte[]> getAllImages() throws IOException {
        List<Gallery> galleries = galleryRepository.findAll();
        List<byte[]> imageBytes = new ArrayList<>();

        for (Gallery gallery : galleries) {
            String imageUrl = gallery.getImage_url();
            if (!imageUrl.isEmpty()) {
                try {
                    byte[] imageData = getImage(imageUrl);
                    imageBytes.add(imageData);
                } catch (IOException e) {
                    System.err.println("Error fetching image from URL: " + imageUrl);
                    e.printStackTrace();
                }
            } else {
                System.err.println("Invalid image URL for gallery: " + gallery.getId());
            }
        }

        return imageBytes;
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

    //TODO: Delete previous image
    public Gallery updatePreviewImageForRoom(RoomType roomType, String fileName, String format, MultipartFile multipartFile) throws IOException {
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
