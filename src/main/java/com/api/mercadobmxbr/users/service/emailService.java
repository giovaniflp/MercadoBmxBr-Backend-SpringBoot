package com.api.mercadobmxbr.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class emailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public void enviarEmailDeTexto(String destinatario, String assunto, String mensagem){
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(remetente);
            mail.setTo(destinatario);
            mail.setSubject(assunto);
            mail.setText(mensagem);
            javaMailSender.send(mail);
        } catch (Exception ignored){
        }
    }


}
