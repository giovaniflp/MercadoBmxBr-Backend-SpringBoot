package com.api.mercadobmxbr.advertisement.repository;

import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface advertisementRepository extends MongoRepository<advertisementModel, Integer> {
    advertisementModel findById(String id);
    void deleteById(String id);
    List<advertisementModel> findByIdUsuario(String userId);
    List<advertisementModel> findByCategoria(String categoria);

    List<advertisementModel> findByLocalidadeAndEstadoDaPecaAndCategoria(String localidade, String estadoDaPeca, String category);

    List<advertisementModel> findByLocalidadeAndCategoria(String localidade, String category);

    List<advertisementModel> findByEstadoDaPecaAndCategoria(String estadoDaPeca, String category);
}
