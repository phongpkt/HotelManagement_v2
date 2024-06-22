package com.example.HotelManagement.service.Room;

import com.example.HotelManagement.dto.CreateRoomTypeDTO;
import com.example.HotelManagement.model.Gallery;
import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.repository.TypeRepository;
import com.example.HotelManagement.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public RoomType save(CreateRoomTypeDTO newType) {
        RoomType newRoom = new RoomType();
        newRoom.setName(newType.getName());
        newRoom.setDescription(newType.getDescription());
        newRoom.setCapacity(newType.getCapacity());
        newRoom.setPricePerNight(newType.getPricePerNight());
        return typeRepository.save(newRoom);
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

    public RoomType updatePreviewImage(Long roomId, Long imageId) {
        Optional<RoomType> optionalRoomType = typeRepository.findById(roomId);
        Optional<Gallery> optionalGallery = galleryService.findById(imageId);

        if (optionalRoomType.isPresent() && optionalGallery.isPresent()) {
            RoomType roomType = optionalRoomType.get();
            Gallery gallery = optionalGallery.get();

            // Set the preview image of the RoomType
            roomType.setPreviewImage(gallery);
            gallery.setRoomType(roomType);

            // Save updated RoomType entity
            return typeRepository.save(roomType);
        }

        throw new RuntimeException("Room type or gallery not found with ids: " + roomId + ", " + imageId);
    }


    public RoomType saveRoomTypeImages(Long id, String fileName, String format, MultipartFile multipartFile) throws IOException {
        Optional<RoomType> updatedType = typeRepository.findById(id);
        if (updatedType.isPresent()) {
            galleryService.saveRoomTypeImages(updatedType.get(), fileName, format, multipartFile);
            return updatedType.get();
        }
        return null;
    }

    public byte[] getImage(String imageURL) throws IOException {
        return galleryService.getImage(imageURL);
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
