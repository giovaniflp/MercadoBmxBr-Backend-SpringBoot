package com.api.mercadobmxbr.users.service;

import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class emailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public emailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmailDeTexto(String destinatario, String assunto, String mensagem){

        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("mercadobmxbr@gmail.com");
            mail.setTo(destinatario);
            mail.setSubject(assunto);
            mail.setText(mensagem);
            javaMailSender.send(mail);
        } catch (Exception ignored){
            System.out.println("Erro ao enviar email");
        }
    }

}
