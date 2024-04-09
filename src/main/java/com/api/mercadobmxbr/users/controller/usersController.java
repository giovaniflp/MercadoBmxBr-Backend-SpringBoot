package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.model.usersModel;

import jakarta.annotation.security.PermitAll;

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
    @PermitAll
    public List<usersModel> findAllUsers() {
        return usersService.findAllUsers();
    }

    @GetMapping("/{id}")
    public usersModel findUserById(@PathVariable String id) {
        return usersService.findUserById(id);
    }

    @PostMapping("/register")
    @PermitAll
    public usersModel registerUser(@RequestBody usersModel userData) {
        return usersService.registerUser(userData);
    }

    @PostMapping("/login")
    @PermitAll
    public String loginUser(@RequestBody String email, String password){
        return "Implement";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        return usersService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public usersModel updateUser(@PathVariable String id, @RequestBody usersModel userData) {
        return usersService.updateUser(id, userData);
    }

}