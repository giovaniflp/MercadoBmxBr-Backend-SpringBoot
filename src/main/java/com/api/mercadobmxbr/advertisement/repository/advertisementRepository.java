package com.api.mercadobmxbr.advertisement.repository;

import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface advertisementRepository extends MongoRepository<advertisementModel, Integer> {
    advertisementModel findById(String id);
}
