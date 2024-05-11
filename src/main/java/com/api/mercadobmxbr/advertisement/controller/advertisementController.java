package com.api.mercadobmxbr.advertisement.controller;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.service.advertisementService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/advertisements")
public class advertisementController {

    @Autowired
    private advertisementService advertisementService;

    @GetMapping("/all")
    public List<advertisementModel> findAllAdvertisements() {
        return advertisementService.findAllAdvertisements();
    }

    @GetMapping("/{id}")
    public advertisementModel findAdvertisementById(@PathVariable String id) {
        return advertisementService.findAdvertisementById(id);
    }

    @GetMapping("/category/{category}")
    public List<advertisementModel> findAdvertisementByCategory(@PathVariable String category) {
        return advertisementService.findAdvertisementByCategory(category);
    }

    @GetMapping("/category/{category}/localidade/{localidade}")
    public List<advertisementModel> findByLocalidadeAndCategoria(@PathVariable String localidade, @PathVariable String category){
        return advertisementService.findByLocalidadeAndCategoria(localidade, category);
    }

    @GetMapping("/category/{category}/estadoDaPeca/{estadoDaPeca}")
    public List<advertisementModel> findByEstadoDaPecaAndCategoria(@PathVariable String estadoDaPeca, @PathVariable String category){
        return advertisementService.findByEstadoDaPecaAndCategoria(estadoDaPeca, category);
    }

    @GetMapping("category/{category}/localidade/{localidade}/estadoDaPeca/{estadoDaPeca}")
    public List<advertisementModel> findByLocalidadeAndEstadoDaPecaAndCategoria(@PathVariable String localidade, @PathVariable String estadoDaPeca, @PathVariable String category){
        return advertisementService.findByLocalidadeAndEstadoDaPecaAndCategoria(localidade, estadoDaPeca, category);
    }

    @GetMapping("/user/{userId}")
    public List<advertisementModel> findAdvertisementByUser(@PathVariable String userId) {
        return advertisementService.findAdvertisementByUser(userId);
    }

    @PostMapping("/register")
    public advertisementModel registerAdvertisement(@RequestBody advertisementModel advertisementModel) {
        return advertisementService.registerAdvertisement(advertisementModel);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdvertisement(@PathVariable String id) {
        advertisementService.deleteAdvertisement(id);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return advertisementService.uploadImage(file);
    }

    @PatchMapping("/patch/{id}")
    public advertisementModel patchAdvertisement(@RequestBody advertisementModel advertisementModel, @PathVariable String id) {
        return advertisementService.patchAdvertisement(id, advertisementModel);
    }

}
