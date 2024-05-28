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
    public String registerUser(usersModel userData) {
        usersModel user = usersRepository.findByEmail(userData.getEmail());
        if(user != null){
            return "Email já cadastrado!";
        } else if(userData.getPassword().length() < 6){
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres!");
        }  else{
            userData.setPassword(securityConfig.bCryptPasswordEncoder().encode(userData.getPassword()));
            userData.setActivationSituation(false);
            userData.setVerificationCode("");
            emailService.enviarEmailDeTexto(userData.getEmail(), "Bem-vindo ao MercadoBmxBr", "Olá, " + userData.getName() + "! Seja bem-vindo ao MercadoBmxBr!");
            usersRepository.save(userData);
            return "Usuário cadastrado com sucesso!";
        }
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
    public void verificationCodeNewEmail(String newEmail) {
        String codeGenerate = UUID.randomUUID().toString().substring(0, 6);
        usersModel user = usersRepository.findByNewEmail(newEmail);
        user.setVerificationCode(codeGenerate);
        usersRepository.save(user);
        emailService.enviarEmailDeTexto(newEmail, "Código de verificação", "Seu código de verificação é: " + codeGenerate);
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
        if(email == user.getNewEmail()){
            if (user.getVerificationCode().equals(code)){
                user.setEmail(user.getNewEmail());
                user.setNewEmail("");
                usersRepository.save(user);
            } else {
                throw new RuntimeException("Código de verificação inválido!");
            }
        } else{
            if (user.getVerificationCode().equals(code)) {
                user.setActivationSituation(true);
                usersRepository.save(user);
            } else {
                throw new RuntimeException("Código de verificação inválido!");
            }
        }
    }

    @Transactional
    public void activateNewEmail(String newEmail, String code) {
        usersModel user = usersRepository.findByNewEmail(newEmail);
            if (user.getVerificationCode().equals(code)){
                user.setEmail(user.getNewEmail());
                user.setNewEmail("");
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
    public usersModel patchUser(String id, String name, String email, String senhaAntiga, String senhaNova) {
        usersModel user = usersRepository.findById(id);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }

        if (email != null && !email.isEmpty()) {
            String codeGenerate = UUID.randomUUID().toString().substring(0, 6);
            user.setNewEmail(email);
            user.setVerificationCode(codeGenerate);
            emailService.enviarEmailDeTexto(email, "Código de verificação", "Seu novo código de verificação é: " + codeGenerate);
        }

        if (senhaAntiga != null && !senhaAntiga.isEmpty()) {
            if (!securityConfig.bCryptPasswordEncoder().matches(senhaAntiga, user.getPassword())) {
                throw new RuntimeException("Senha antiga inválida!");
            }

            if (senhaNova != null && !senhaNova.isEmpty()) {
                user.setPassword(securityConfig.bCryptPasswordEncoder().encode(senhaNova));
            }
        }

        return usersRepository.save(user);
    }
}
