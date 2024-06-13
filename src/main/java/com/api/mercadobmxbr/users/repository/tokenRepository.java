package com.api.mercadobmxbr.users.repository;

import com.api.mercadobmxbr.users.model.usersModel;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface tokenRepository extends MongoRepository<usersModel, Integer> {

    // Alterando o tipo de retorno dos métodos e parâmetros recebidos
    Optional<usersModel> findByEmail(String email);

}