package com.api.mercadobmxbr.favorites.repository;

import com.api.mercadobmxbr.favorites.model.favoriteModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface favoriteRepository extends MongoRepository<favoriteModel, Integer> {
    void deleteById(String id);
    void deleteByIdAnuncio(String idAnuncio);
    void deleteByIdUsuario(String idUsuario);

    favoriteModel findById(String id);
    List<favoriteModel> findByIdUsuario(String idUsuario);
}
