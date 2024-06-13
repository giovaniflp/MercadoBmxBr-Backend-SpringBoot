package com.api.mercadobmxbr.favorites.repository;

import com.api.mercadobmxbr.favorites.model.favoriteModel;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface favoriteRepository extends MongoRepository<favoriteModel, Integer> {

    favoriteModel findById(String id);

    List<favoriteModel> findByIdUsuario(String idUsuario);

    void deleteByIdAnuncio(String idAnuncio);
    void deleteByIdUsuario(String idUsuario);

}
