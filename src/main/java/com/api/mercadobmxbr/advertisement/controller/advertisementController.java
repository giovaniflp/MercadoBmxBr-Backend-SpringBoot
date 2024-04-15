package com.api.mercadobmxbr.advertisement.controller;
import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import com.api.mercadobmxbr.advertisement.service.advertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public advertisementModel registerAdvertisement(@RequestBody advertisementModel advertisementData) {
        return advertisementService.registerAdvertisement(advertisementData);
    }

}
