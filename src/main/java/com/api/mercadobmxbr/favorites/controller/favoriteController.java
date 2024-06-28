package com.api.mercadobmxbr.favorites.controller;

import com.api.mercadobmxbr.favorites.model.favoriteModel;
import com.api.mercadobmxbr.favorites.service.favoriteService;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/favorites")
public class favoriteController {

    private final favoriteService favoriteService;

    @Autowired
    public favoriteController(favoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{id}")
    public favoriteModel findFavoriteById(@PathVariable String id) {
        return favoriteService.findFavoriteById(id);
    }

    @GetMapping("/all")
    public List<favoriteModel> findAllFavorites() {
        return favoriteService.findAllFavorites();
    }

    @GetMapping("/user/{userId}")
    public List<favoriteModel> findFavoriteByUser(@PathVariable String userId) {
        return favoriteService.findFavoriteByUser(userId);
    }

    @GetMapping("/verify/{idUsuario}/{idAnuncio}")
    public boolean verifyFavorite(@PathVariable String idUsuario, @PathVariable String idAnuncio) {
        return favoriteService.verifyFavorite(idUsuario, idAnuncio);
    }

    @PostMapping("/save")
    public favoriteModel saveFavorite(@RequestBody favoriteModel favoriteModel) {
        return favoriteService.saveFavorite(favoriteModel);
    }

    @DeleteMapping("/delete/{idAnuncio}")
    public void deleteFavorite(@PathVariable String idAnuncio) {
        favoriteService.delete(idAnuncio);
    }

}
