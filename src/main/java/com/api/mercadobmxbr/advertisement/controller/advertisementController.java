package com.api.mercadobmxbr.advertisement.controller;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.service.advertisementService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/category/localidade/{localidade}")
    public List<advertisementModel> findByLocalidade(@PathVariable String localidade){
        return advertisementService.findByLocalidade(localidade);
    }

    @GetMapping("/category/estadoDaPeca/{estadoDaPeca}")
    public List<advertisementModel> findByEstadoDaPeca(@PathVariable String estadoDaPeca){
        return advertisementService.findByEstadoDaPeca(estadoDaPeca);
    }

    @GetMapping("category/localidadeEstadoDaPeca/{localidade}/{estadoDaPeca}")
    public List<advertisementModel> findByLocalidadeAndEstadoDaPeca(@PathVariable String localidade, @PathVariable String estadoDaPeca){
        return advertisementService.findByLocalidadeAndEstadoDaPeca(localidade, estadoDaPeca);
    }

    @GetMapping("/user/{userId}")
    public List<advertisementModel> findAdvertisementByUser(@PathVariable String userId) {
        return advertisementService.findAdvertisementByUser(userId);
    }

    @PostMapping("/register")
    public advertisementModel registerAdvertisement(@RequestBody advertisementModel advertisementData) {
        return advertisementService.registerAdvertisement(advertisementData);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdvertisement(@PathVariable String id) {
        advertisementService.deleteAdvertisement(id);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return advertisementService.uploadImage(file);
    }

}
