package com.api.mercadobmxbr.users.model;

import com.api.mercadobmxbr.users.service.loginRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class usersModel {
    @Id private String id;
    private String name;
    private String email;
    private String password;

    public boolean isLoginCorrect(loginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }


}

