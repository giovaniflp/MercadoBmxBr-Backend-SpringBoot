package com.api.mercadobmxbr.advertisement.service;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.repository.advertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public advertisementModel registerAdvertisement(advertisementModel advertisementData) {
        return advertisementRepository.save(advertisementData);
    }

}
