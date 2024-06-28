package com.api.mercadobmxbr.favorites.service;

import com.api.mercadobmxbr.favorites.repository.favoriteRepository;
import com.api.mercadobmxbr.favorites.model.favoriteModel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class favoriteService {

    private final favoriteRepository favoriteRepository;

    @Autowired
    public favoriteService(favoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public favoriteModel findFavoriteById(String id){
        return favoriteRepository.findById(id);
    }

    public favoriteModel saveFavorite(favoriteModel favoriteModel) {
        return favoriteRepository.save(favoriteModel);
    }

    public List<favoriteModel> findAllFavorites() {
        return favoriteRepository.findAll();
    }

    public List<favoriteModel> findFavoriteByUser(String idUsuario){
        return favoriteRepository.findByIdUsuario(idUsuario);
    }

    public void delete(String idAnuncio) {
        favoriteRepository.deleteByIdAnuncio(idAnuncio);
    }

    public boolean verifyFavorite(String idUsuario, String idAnuncio) {
        List<favoriteModel> favorites = favoriteRepository.findByIdUsuario(idUsuario);
        for (favoriteModel favorite : favorites) {
            if (favorite.getIdAnuncio().equals(idAnuncio)) {
                return true;
            }
        }
        return false;
    }

}
