package com.api.mercadobmxbr.users.service;

import com.api.mercadobmxbr.users.repository.usersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.mercadobmxbr.users.model.usersModel;
import org.springframework.transaction.annotation.Transactional;
import com.api.mercadobmxbr.security.securityConfig;
import com.api.mercadobmxbr.users.repository.tokenRepository;

import java.util.List;
import java.util.UUID;

@Service
public class usersService {

    @Autowired
    private usersRepository usersRepository;

    @Autowired
    private securityConfig securityConfig;

    @Autowired
    private emailService emailService;

    @Transactional
    public List<usersModel> findAllUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    public usersModel findUserById(String id) {
        return usersRepository.findById(id);
    }

    @Transactional
    public usersModel registerUser(usersModel userData) {
        userData.setPassword(securityConfig.bCryptPasswordEncoder().encode(userData.getPassword()));
        userData.setActivationSituation(false);
        userData.setVerificationCode("");
        emailService.enviarEmailDeTexto(userData.getEmail(), "Bem-vindo ao MercadoBmxBr", "Olá, " + userData.getName() + "! Seja bem-vindo ao MercadoBmxBr!");
        return usersRepository.save(userData);
    }

    @Transactional
    public void verificationCode(String email) {
        String codeGenerate = UUID.randomUUID().toString().substring(0, 6);
        usersModel user = usersRepository.findByEmail(email);
        user.setVerificationCode(codeGenerate);
        usersRepository.save(user);
        emailService.enviarEmailDeTexto(email, "Código de verificação", "Seu código de verificação é: " + codeGenerate);
    }

    @Transactional
    public void lostPassword(String email) {
        String randomPassword = UUID.randomUUID().toString().substring(0, 6);
        usersModel user = usersRepository.findByEmail(email);
        user.setPassword(securityConfig.bCryptPasswordEncoder().encode(randomPassword));
        usersRepository.save(user);
        emailService.enviarEmailDeTexto(email, "Recuperação de senha", "Quando logar troque para uma senha de sua escolha! sua senha provisória é essa: " + randomPassword);
    }

    @Transactional
    public void activateUser(String email, String code) {
        usersModel user = usersRepository.findByEmail(email);
        if (user.getVerificationCode().equals(code)) {
            user.setActivationSituation(true);
            usersRepository.save(user);
        } else {
            throw new RuntimeException("Código de verificação inválido!");
        }
    }

    @Transactional
    public String deleteUser(String id) {
        usersRepository.deleteById(id);
        return "Usuário deletado com sucesso!";
    }

    @Transactional
    public usersModel patchUser(String id,  String name, String email, String senhaAntiga, String senhaNova) {
        usersModel user = usersRepository.findById(id);
        if(name != null && !name.isEmpty()){
            user.setName(name);
        }
        if(email != null && !email.isEmpty()){
            String codeGenerate = UUID.randomUUID().toString().substring(0, 6);
            user.setEmail(email);
            user.setActivationSituation(false);
            user.setVerificationCode(codeGenerate);
            emailService.enviarEmailDeTexto(email, "Código de verificação", "Seu novo código de verificação é: " + codeGenerate);
        }
        if(senhaNova != null && !senhaNova.isEmpty() && securityConfig.bCryptPasswordEncoder().matches(senhaAntiga, user.getPassword())){
            user.setPassword(securityConfig.bCryptPasswordEncoder().encode(senhaNova));
        }
        return usersRepository.save(user);
        }
    }
