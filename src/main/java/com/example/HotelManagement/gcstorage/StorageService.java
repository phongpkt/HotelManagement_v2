package com.example.HotelManagement.gcstorage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {

    private static final Logger LOG = LoggerFactory.getLogger(StorageService.class);
    private final Storage storage;
    private final String bucketName;
    private final String root;

    public StorageService(final Storage storage,
                          @Value("${app.config.bucket-name}") final String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
        this.root = "https://storage.googleapis.com/" + bucketName + "/";
    }

    public byte[] getFile(String directory, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, directory + "/" + fileName);
        String fileExtension = getFileExtension(fileName);
        MediaType contentType;

        if ("png".equalsIgnoreCase(fileExtension)) {
            contentType = MediaType.IMAGE_PNG;
        } else if ("jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)) {
            contentType = MediaType.IMAGE_JPEG;
        } else {
            throw new UnsupportedMediaTypeException("Unsupported image format: " + fileExtension);
        }
        Blob blob = storage.get(blobId);
        if (blob != null) {
            return blob.getContent();
        }
        throw new FileNotFoundException("File not found: " + fileName);
    }

    public List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();
        Iterable<Blob> blobs = storage.list(bucketName).iterateAll();
        for (Blob blob : blobs) {
            String fileName = blob.getName();
            String imagePath = root + fileName;
            imagePaths.add(imagePath);
        }

        return imagePaths;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        String objectName = folderName + "/" + file.getOriginalFilename();
        Blob blob = storage.create(
                Blob.newBuilder(bucketName, objectName)
                        .build(),
                file.getBytes());

        return objectName;
    }

    public byte[] getFileFromStorage(String imageUrl) throws IOException {
        String fileName = extractFileNameFromUrl(imageUrl);
        BlobId blobId = BlobId.of(bucketName, fileName);
        Blob blob = storage.get(blobId);
        if (blob != null) {
            return blob.getContent();
        }
        return null;
    }

    private String extractFileNameFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}