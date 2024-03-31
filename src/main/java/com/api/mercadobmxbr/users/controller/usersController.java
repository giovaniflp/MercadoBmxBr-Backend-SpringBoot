package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.model.usersModel;
import com.api.mercadobmxbr.users.repository.usersRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class usersController {
    @Autowired
    private usersRepository repository;

    @GetMapping("/all")
    public List<usersModel> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public usersModel findByIdString(@PathVariable ObjectId id) {
       return repository.findById(id);
    }

    @PostMapping("/save")
    public usersModel save(@RequestBody usersModel user) {
        return repository.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable ObjectId id) {
        repository.deleteById(id);
        return "Usuário deletado com sucesso!";
    }
}
