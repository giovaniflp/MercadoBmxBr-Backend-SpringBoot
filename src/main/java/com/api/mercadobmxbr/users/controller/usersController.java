package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.model.usersModel;
import com.api.mercadobmxbr.users.repository.usersRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.api.mercadobmxbr.users.service.usersService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class usersController {

    @Autowired
    usersService usersService;

    @GetMapping
    public List<usersModel> findAllUsers() {
        return usersService.findAllUsers();
    }

    @GetMapping("/{id}")
    public usersModel findUserById(@PathVariable String id) {
        return usersService.findUserById(id);
    }

    @PostMapping
    public usersModel registerUser(@RequestBody usersModel userData) {
        return usersService.registerUser(userData);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        return usersService.deleteUser(id);
    }
}
