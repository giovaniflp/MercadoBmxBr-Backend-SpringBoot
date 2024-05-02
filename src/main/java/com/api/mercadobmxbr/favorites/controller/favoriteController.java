package com.api.mercadobmxbr.favorites.controller;

import com.api.mercadobmxbr.favorites.model.favoriteModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.mercadobmxbr.favorites.service.favoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/favorites")
public class favoriteController {

    @Autowired
    private favoriteService favoriteService;

    @GetMapping("/all")
    public List<favoriteModel> findAllFavorites() {
        return favoriteService.findAllFavorites();
    }

    @GetMapping("/{id}")
    public favoriteModel findFavoriteById(@PathVariable String id) {
        return favoriteService.findFavoriteById(id);
    }

    @GetMapping("/user/{userId}")
    public List<favoriteModel> findFavoriteByUser(@PathVariable String userId) {
        return favoriteService.findFavoriteByUser(userId);
    }

    @PostMapping("/save")
    public favoriteModel saveFavorite(@RequestBody favoriteModel favoriteModel) {
        return favoriteService.saveFavorite(favoriteModel);
    }

    @DeleteMapping("/delete/{idAnuncio}")
    public void deleteFavorite(@PathVariable String idAnuncio) {
        favoriteService.delete(idAnuncio);
    }

    @GetMapping("/verify/{idUsuario}/{idAnuncio}")
    public boolean verifyFavorite(@PathVariable String idUsuario, @PathVariable String idAnuncio) {
        return favoriteService.verifyFavorite(idUsuario, idAnuncio);
    }

}
