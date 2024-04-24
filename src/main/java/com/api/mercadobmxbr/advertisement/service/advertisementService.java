package com.api.mercadobmxbr.advertisement.service;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.repository.advertisementRepository;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class advertisementService {

    @Autowired
    private advertisementRepository advertisementRepository;

    @Transactional
    public List<advertisementModel> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    @Transactional
    public advertisementModel findAdvertisementById(String id){
        return advertisementRepository.findById(id);
    }

    @Transactional
    public List<advertisementModel> findAdvertisementByCategory(String category){
        return advertisementRepository.findByCategoria(category);
    }

    @Transactional
    public List<advertisementModel> findAdvertisementByUser(String userId){
        return advertisementRepository.findByIdUsuario(userId);
    }

    @Transactional
    public advertisementModel registerAdvertisement(advertisementModel advertisementModel){
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        advertisementModel.setDataPostagem(formattedDate);
        return advertisementRepository.save(advertisementModel);
    }

    public String uploadImage(MultipartFile file) throws IOException {

        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=mercadobmxbr;AccountKey=wv3hSdDl89gy9Fj2KzMCTuUfpjiywNyNyLg+HD2AJz9q8B7H1FUTYitjM94BjVfsxhMzr4r88iXt+AStwfdpVA==;EndpointSuffix=core.windows.net")
                .containerName("advertisements")
                .buildClient();

        BlobClient blob = containerClient.getBlobClient(file.getOriginalFilename());

        blob.upload(file.getInputStream(), file.getSize(), true);
        return blob.getBlobUrl();
    }

    @Transactional
    public void deleteAdvertisement(String id) {
        advertisementModel advertisement = advertisementRepository.findById(id);
        if (advertisement != null) {
            // Extract image file name from the URL
            String imageUrl = advertisement.getImagem();
            String fileName = extractFileNameFromUrl(imageUrl);

            // Delete advertisement from database
            advertisementRepository.deleteById(id);

            // Delete image from Azure storage
            if (fileName != null) {
                deleteImage(fileName);
            }
        }
    }

    private void deleteImage(String fileName) {
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=mercadobmxbr;AccountKey=wv3hSdDl89gy9Fj2KzMCTuUfpjiywNyNyLg+HD2AJz9q8B7H1FUTYitjM94BjVfsxhMzr4r88iXt+AStwfdpVA==;EndpointSuffix=core.windows.net")
                .containerName("advertisements")
                .buildClient();

        BlobClient blob = containerClient.getBlobClient(fileName);
        blob.delete();
    }

    private String extractFileNameFromUrl(String url) {
        // Extract file name from the URL
        int index = url.lastIndexOf("/");
        if (index != -1) {
            return url.substring(index + 1);
        }
        return null;
    }
}
