package com.api.mercadobmxbr.users.repository;

import com.api.mercadobmxbr.users.model.usersModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface tokenRepository extends MongoRepository<usersModel, Integer> {
    // Alterando o tipo de retorno dos métodos e parâmetros recebidos
    Optional<usersModel> findByEmail(String email);
}