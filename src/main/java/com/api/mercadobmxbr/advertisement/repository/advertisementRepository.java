package com.api.mercadobmxbr.advertisement.repository;

import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface advertisementRepository extends MongoRepository<advertisementModel, Integer> {
    advertisementModel findById(String id);

    List<advertisementModel> findByIdUsuario(String userId);
}
