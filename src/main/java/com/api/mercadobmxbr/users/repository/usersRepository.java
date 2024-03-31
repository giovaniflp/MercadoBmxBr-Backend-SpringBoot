package com.api.mercadobmxbr.users.repository;

import com.api.mercadobmxbr.users.model.usersModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface usersRepository
extends MongoRepository <usersModel, Integer> {
// Alterando o tipo de retorno do método findById
    usersModel findById(ObjectId id);
    usersModel deleteById(ObjectId id);
}
