package com.api.mercadobmxbr.advertisement.controller;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.service.advertisementService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/pagination")
    public Page<advertisementModel> getAdvertisements(
            @RequestParam(defaultValue = "all") String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "preco") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc) {
        return advertisementService.getAdvertisements(categoria, page, size, sortBy, asc);
    }

    @GetMapping("/{id}")
    public advertisementModel findAdvertisementById(@PathVariable String id) {
        return advertisementService.findAdvertisementById(id);
    }

    @GetMapping("/category/{category}")
    public Page<advertisementModel> findAdvertisementByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return advertisementService.findAdvertisementByCategory(category, page, size);
    }

    @PostMapping("/category/{category}/filter")
    public Page<advertisementModel> findAdvertisementByCategoryAndFilter(@PathVariable String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestBody Map<String, String> requestBody) {
        String localidade = requestBody.get("localidade");
        String estadoDaPeca = requestBody.get("estadoDaPeca");
        String dataPostagem = requestBody.get("dataPostagem");
        String marca = requestBody.get("marca");
        String valor = requestBody.get("valor");
        return advertisementService.findAdversitementByCategoryAndFilter(category, localidade, estadoDaPeca, dataPostagem, marca, valor, page, size);
    }

    @PostMapping("/dataPostagem/estadoDaPeca")
    public Page<advertisementModel> findByDataPostagemAndEstadoDaPeca(@RequestBody Map<String, String> requestBody){
        String estadoDaPeca = requestBody.get("estadoDaPeca");
        return advertisementService.findAdvertisementByDataPostagemAndEstadoDaPeca(estadoDaPeca, 0, 5);
    }

    @PostMapping("/localidade")
    public Page<advertisementModel> findByDataPostagemAndLocalidade(@RequestBody Map<String, String> requestBody){
        String localidade = requestBody.get("localidade");
        return advertisementService.findAdvertisementByLocalidade(localidade, 0, 5);
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
