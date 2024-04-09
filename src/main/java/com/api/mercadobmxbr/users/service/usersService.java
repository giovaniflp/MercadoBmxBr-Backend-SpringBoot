package com.api.mercadobmxbr.users.service;

import com.api.mercadobmxbr.users.repository.usersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.mercadobmxbr.users.model.usersModel;
import org.springframework.transaction.annotation.Transactional;
import com.api.mercadobmxbr.security.securityConfig;

import java.util.List;

@Service
public class usersService {

    @Autowired
    private usersRepository usersRepository;

    @Autowired
    private securityConfig securityConfig;

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
        userData.setPassword(securityConfig.bCryptPasswordEncoder().encode(userData.getPassword()));
        return usersRepository.save(userData);
    }

    @Transactional
    public String deleteUser(String id) {
        usersRepository.deleteById(id);
        return "Usuário deletado com sucesso!";
    }

    @Transactional
    public usersModel updateUser(String id, usersModel userData) {
        usersModel user = usersRepository.findById(id);
        if (userData.getName() != null) {
            user.setName(userData.getName());
        }
        if (userData.getEmail() != null){
            user.setEmail(userData.getEmail());
        }
        if (userData.getPassword() != null){
            user.setPassword(userData.getPassword());
        }
        return usersRepository.save(user);
    }

}
