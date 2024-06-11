package com.api.mercadobmxbr.advertisement.repository;

import com.api.mercadobmxbr.advertisement.model.advertisementModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface advertisementRepository extends MongoRepository<advertisementModel, Integer> {

    Page<advertisementModel> findAll(Pageable pageable);
    Page<advertisementModel> findByCategoria(String categoria, Pageable pageable);
    Page<advertisementModel> findByEstadoDaPeca(String estadoDaPeca,Pageable pageable);
    Page<advertisementModel> findByLocalidade(String localidade, Pageable pageable);

    Page<advertisementModel> findByLocalidadeAndCategoria(String localidade, String category, Pageable pageable);
    Page<advertisementModel> findByEstadoDaPecaAndCategoria(String estadoDaPeca, String category, Pageable pageable);
    Page<advertisementModel> findByMarcaAndCategoria(String marca, String category, Pageable pageable);
    Page<advertisementModel> findByLocalidadeAndEstadoDaPecaAndMarcaAndCategoria(String localidade, String estadoDaPeca, String marca, String category, Pageable pageable);
    Page<advertisementModel> findByLocalidadeAndEstadoDaPecaAndCategoria(String localidade, String estadoDaPeca, String category, Pageable pageable);
    Page<advertisementModel> findByLocalidadeAndMarcaAndCategoria(String localidade, String marca, String category, Pageable pageable);
    Page<advertisementModel> findByEstadoDaPecaAndMarcaAndCategoria(String estadoDaPeca, String marca, String category, Pageable pageable);

    void deleteByIdUsuario(String idUsuario);

    advertisementModel findById(String id);
    void deleteById(String id);
    List<advertisementModel> findByIdUsuario(String idUsuario);

}
