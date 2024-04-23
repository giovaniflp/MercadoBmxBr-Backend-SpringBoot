package com.api.mercadobmxbr.users.controller;

import com.api.mercadobmxbr.users.service.loginRequest;
import com.api.mercadobmxbr.users.service.loginResponse;
import com.api.mercadobmxbr.users.service.usersService;


import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.api.mercadobmxbr.users.repository.usersRepository;
import com.api.mercadobmxbr.users.repository.tokenRepository;

import java.text.ParseException;
import java.time.Instant;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/token")
public class tokenController {

    @Autowired
    JwtEncoder jwtEncoder;

    @Autowired
    JwtDecoder jwtDecoder;

    @Autowired
    tokenRepository tokenRepository;

    @Autowired
    usersService usersService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest loginRequest) {

        var user = tokenRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        } else if (user.get().getActivationSituation() == false) {
            return ResponseEntity.badRequest().build();
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

    @GetMapping("/jwtDecode")
    public Map<String, Object> jwtDecode(@RequestHeader("Authorization") String authorization) throws ParseException {
        var token = authorization.replace("Bearer ", "");
        var jwt = SignedJWT.parse(token);
        return jwt.getJWTClaimsSet().getClaims();
    }

}
