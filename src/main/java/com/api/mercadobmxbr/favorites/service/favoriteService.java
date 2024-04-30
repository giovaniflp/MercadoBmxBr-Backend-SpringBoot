package com.api.mercadobmxbr.favorites.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.mercadobmxbr.favorites.repository.favoriteRepository;
import com.api.mercadobmxbr.favorites.model.favoriteModel;

import java.util.List;

@Service
public class favoriteService {

    @Autowired
    private favoriteRepository favoriteRepository;

    public List<favoriteModel> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    public favoriteModel findFavoriteById(String id){
        return favoriteRepository.findById(id);
    }

    public List<favoriteModel> findFavoriteByUser(String idUsuario){
        return favoriteRepository.findByIdUsuario(idUsuario);
    }

    public favoriteModel saveFavorite(favoriteModel favoriteModel) {
        return favoriteRepository.save(favoriteModel);
    }

    public void delete(String id) {
        favoriteRepository.deleteById(id);
    }

}
