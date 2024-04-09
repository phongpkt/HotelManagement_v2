package com.example.HotelManagement.service.Room;

import com.example.HotelManagement.model.RoomType;
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
import java.util.List;
import java.util.Optional;

import static com.example.HotelManagement.specifications.TypeSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    private final String root = "src/main/resources/static/images/";

    public List<RoomType> findAll(){
        return typeRepository.findAll();
    }
    public Optional<RoomType> findById(Long id){
        return typeRepository.findById(id);
    }

    public Optional<RoomType> findByName(String name) {
        return typeRepository.findOne(where(hasName(name)));
    }

    public List<RoomType> findByPriceLessThan(Double price) {
        return typeRepository.findAll(hasPriceLessThan(price));
    }

    public List<RoomType> findByCapacityGreaterThan(Integer capacity) {
        return typeRepository.findAll(hasCapacityGreaterThan(capacity));
    }

    public RoomType save(RoomType newType) {
        return typeRepository.save(newType);
    }
    public boolean findDuplicateRoomType(RoomType type){
        List<RoomType> foundType = typeRepository.findByName(type.getName().trim());
        return !foundType.isEmpty();
    }
    public RoomType update(RoomType newType, Long id){
        RoomType updatedType = typeRepository.findById(id).map(type -> {
            type.setName(newType.getName());
            type.setDescription(newType.getDescription());
            type.setPricePerNight(newType.getPricePerNight());
            type.setCapacity(newType.getCapacity());
            type.setPricePerNight(newType.getPricePerNight());
            return typeRepository.save(type);
        }).orElseGet(() -> {
            newType.setId(id);
            return typeRepository.save(newType);
        });
        return updatedType;
    }

    public RoomType updateImage(Long id, String fileName, MultipartFile multipartFile) throws IOException {
        Optional<RoomType> updatedType = typeRepository.findById(id);
        String uploadDir = root + id;
        if (updatedType.isPresent()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
            updatedType.get().setPreview_image_url(fileName);
            typeRepository.save(updatedType.get());
            return updatedType.get();
        }
        return null;
    }

    public byte[] getImage(Long id, String imageName) throws IOException {
        String imageDirectory = root + id;
        Path imagePath = Path.of(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } else {
            return null; // Handle missing images
        }
    }

    public boolean delete(Long id) {
        boolean exists = typeRepository.existsById(id);
        if(exists) {
            typeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
