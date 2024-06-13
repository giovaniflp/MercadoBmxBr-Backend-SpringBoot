package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.model.usersModel;
import com.api.mercadobmxbr.users.service.usersService;

import java.util.List;
import java.util.Map;

import jakarta.annotation.security.PermitAll;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    public String registerUser(@RequestBody usersModel userData) {
        return usersService.registerUser(userData);
    }

    @PostMapping("/sendCode")
    @PermitAll
    public void verificationCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        usersService.verificationCode(email);
    }

    @PostMapping("/sendCodeNewEmail")
    @PermitAll
    public void verificationCodeNewEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        usersService.verificationCodeNewEmail(email);
    }

    @PostMapping("/activate")
    @PermitAll
    public void activateUser(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String code = requestBody.get("code");
        usersService.activateUser(email, code);
    }

    @PostMapping("/activateNewEmail")
    @PermitAll
    public void activateNewEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String code = requestBody.get("code");
        usersService.activateNewEmail(email, code);
    }

    @PostMapping("/lostPassword")
    @PermitAll
    public void lostPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        usersService.lostPassword(email);
    }

    @PatchMapping("/patch/{id}")
    public usersModel patchUser(@RequestBody Map<String, String> requestBody, @PathVariable String id) {
        String email = requestBody.get("email");
        String name = requestBody.get("name");
        String senhaAntiga = requestBody.get("senhaAntiga");
        String senhaNova = requestBody.get("senhaNova");
        return usersService.patchUser(id, name, email, senhaAntiga, senhaNova);
    }

    //Delete usando Post pois precisa passar a senha do usuário como parâmetro.
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        return usersService.deleteUser(id, password);
    }

}