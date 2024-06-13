package com.api.mercadobmxbr.users.model;

import com.api.mercadobmxbr.users.service.loginRequest;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class usersModel {

    @NotNull
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String verificationCode;
    @NotNull
    private Boolean activationSituation;
    @Nullable
    private String newEmail;

    //Verificar se isso vai pro service
    public boolean isLoginCorrect(loginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }

}

