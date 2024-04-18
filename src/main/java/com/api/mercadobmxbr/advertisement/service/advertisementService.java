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
    public List<advertisementModel> findAdvertisementByUser(String userId){
        return advertisementRepository.findByIdUsuario(userId);
    }

    @Transactional
    public advertisementModel registerAdvertisement(advertisementModel advertisementModel){
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

}
