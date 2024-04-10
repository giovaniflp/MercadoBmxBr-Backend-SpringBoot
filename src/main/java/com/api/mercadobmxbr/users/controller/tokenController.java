package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.service.loginRequest;
import com.api.mercadobmxbr.users.service.loginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.api.mercadobmxbr.users.repository.usersRepository;

import java.time.Instant;


@RestController
@CrossOrigin
@RequestMapping("/api/token")
public class tokenController {

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    usersRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest loginRequest) {

        var user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 15552000L; // 6 meses em segundos

        var claims = JwtClaimsSet.builder()
                .issuer("mercadoBmxBr-API")
                .id(user.get().getId())
                .subject(user.get().getEmail())
                .claim("name", user.get().getName())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new loginResponse(jwtValue, expiresIn));
    }

}
