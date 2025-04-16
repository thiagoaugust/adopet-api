package br.com.projects.thiago.service;

public interface EmailService {

    void enviarEmail(String to, String subject, String message);

}
