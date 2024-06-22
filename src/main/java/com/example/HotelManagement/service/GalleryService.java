package com.example.HotelManagement.service;

import com.example.HotelManagement.enums.ImageTypes;
import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.repository.GalleryRepository;
import com.example.HotelManagement.repository.TypeRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GalleryService {
    private final String root = "https://storage.googleapis.com/";
    private final String bucket_name = "narcissus-bucket";
    private final String folder = "test";
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private TypeRepository typeRepository;

    public List<String> getAllImages() throws IOException {
        List<Gallery> galleries = galleryRepository.findAll();
        List<String> image_url = new ArrayList<>();
        for (Gallery gallery : galleries){
            image_url.add(gallery.getImage_url());
        }
        return image_url;
    }
    public List<Gallery> getImagesByRoom(Long id) throws IOException{
        return galleryRepository.findByRoomType(id);
    }
    public List<String> getImagesByType(String type) throws IOException{
        ImageTypes imageType = ImageTypes.valueOf(type);
        List<Gallery> galleries = galleryRepository.findByImageType(imageType);
        return galleries.stream()
                .map(Gallery::getImage_url)
                .collect(Collectors.toList());
    }
    public Optional<Gallery> findById(Long id){
        return galleryRepository.findById(id);
    }

    public Gallery saveImage(String typeString, String imageUrl, String imageFormat) {
        Gallery image = new Gallery();
        image.setImage_url(imageUrl);
        image.setImage_format(imageFormat);

        for (ImageTypes type : ImageTypes.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                image.setImage_type(type);
                break;
            }
        }
        return galleryRepository.save(image);
    }

    //TODO: Save to gcs buckets
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
            deleteByFileName(existingImagePath); //delete existing image
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

    //TODO: Save to gcs buckets
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

    public void deleteByFileName(String fileName) {
        String imageUrl = root + bucket_name + "/" + folder + "/" + fileName;
        galleryRepository.deleteByImageUrl(imageUrl);
    }
}
