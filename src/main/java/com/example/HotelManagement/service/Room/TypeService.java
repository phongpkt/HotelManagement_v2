package com.example.HotelManagement.service.Room;

import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.repository.TypeRepository;
import com.example.HotelManagement.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.example.HotelManagement.specifications.TypeSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private GalleryService galleryService;

    public List<RoomType> findAll(){
        return typeRepository.findAll();
    }
    public Optional<RoomType> findById(Long id){
        return typeRepository.findById(id);
    }

    public Hotel findHotelByRoom(Long id) {
        return typeRepository.findHotelByRoom(id);
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

    public RoomType updatePreviewImage(Long id, String fileName, MultipartFile multipartFile) throws IOException {
        Optional<RoomType> updatedType = typeRepository.findById(id);
        if (updatedType.isPresent()) {
            Gallery image = galleryService.save(updatedType.get(), fileName, multipartFile);
            updatedType.get().setPreviewImage(image);
            typeRepository.save(updatedType.get());
            return updatedType.get();
        }
        return null;
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
    public boolean delete(Long id) {
        boolean exists = typeRepository.existsById(id);
        if(exists) {
            typeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
