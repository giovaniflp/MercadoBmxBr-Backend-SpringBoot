package com.api.mercadobmxbr.favorites.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "favorites")
public class favoriteModel {

    @NotNull
    @Id
    private String id;

    @NotNull
    private String idUsuario;

    @NotNull
    private String idAnuncio;

}
