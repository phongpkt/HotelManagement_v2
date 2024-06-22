package com.example.HotelManagement.gcstorage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    public String uploadFile(MultipartFile file) throws IOException {
        String objectName = "images" + "/" + file.getOriginalFilename();
        Blob blob = storage.create(
                Blob.newBuilder(bucketName, objectName)
                        .build(),
                file.getBytes());
        return root + objectName;
    }

    public Boolean deleteFile(String fileName) {
        String objectName = "images" + "/" + fileName;
        BlobId blobId = BlobId.of(bucketName, objectName);
        return storage.delete(blobId);
    }
}
