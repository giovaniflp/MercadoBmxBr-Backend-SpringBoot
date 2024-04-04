package com.api.mercadobmxbr.users.service;

import com.api.mercadobmxbr.users.repository.usersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.mercadobmxbr.users.model.usersModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class usersService {

    @Autowired
    private usersRepository usersRepository;

    @Transactional
    public List<usersModel> findAllUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    public usersModel findUserById(String id){
        return usersRepository.findById(id);
    }

    @Transactional
    public usersModel registerUser(usersModel userData) {
        return usersRepository.save(userData);
    }

    @Transactional
    public String deleteUser(String id) {
        usersRepository.deleteById(id);
        return "Usuário deletado com sucesso!";
    }
}
