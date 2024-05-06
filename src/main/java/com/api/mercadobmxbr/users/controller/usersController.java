package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.model.usersModel;

import jakarta.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.api.mercadobmxbr.users.service.usersService;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
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

    @PostMapping("/register")
    @PermitAll
    public usersModel registerUser(@RequestBody usersModel userData) {
        return usersService.registerUser(userData);
    }

    @PostMapping("/sendCode")
    @PermitAll
    public void verificationCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        usersService.verificationCode(email);
    }

    @PostMapping("/activate")
    @PermitAll
    public void activateUser(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String code = requestBody.get("code");
        usersService.activateUser(email, code);
    }

    @PostMapping("/lostPassword")
    @PermitAll
    public void lostPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        usersService.lostPassword(email);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        return usersService.deleteUser(id);
    }

    @PatchMapping("/patch/{id}")
    public usersModel patchUser(@RequestBody Map<String, String> requestBody, @PathVariable String id) {
        String email = requestBody.get("email");
        String name = requestBody.get("name");
        String senhaAntiga = requestBody.get("senhaAntiga");
        String senhaNova = requestBody.get("senhaNova");
        return usersService.patchUser(id, name, email, senhaAntiga, senhaNova);
    }
}