package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceDevTest {

    private EmailServiceDev emailService = new EmailServiceDev();

    @Test
    void deveriaExecutarMetodoDeEnvioDeEmailSemErros() {
        assertDoesNotThrow(() -> {
            emailService.enviarEmail(
                    "teste@email.com",
                    "Assunto de Teste",
                    "Mensagem de Teste"
            );
        });
    }

}