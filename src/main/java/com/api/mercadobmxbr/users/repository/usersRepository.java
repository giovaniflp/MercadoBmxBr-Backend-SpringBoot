package com.api.mercadobmxbr.users.repository;

import com.api.mercadobmxbr.users.model.usersModel;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface usersRepository extends MongoRepository <usersModel, Integer> {

// Alterando o tipo de retorno dos métodos e parâmetros recebidos
    usersModel findById(String id);
    usersModel findByEmail(String email);
    usersModel findByNewEmail(String newEmail);

    void deleteById(String id);

}
