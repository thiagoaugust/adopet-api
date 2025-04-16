package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceProducaoTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailServiceProducao emailService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    void deveriaEnviarEmailComDadosCorretos() {
        String to = "destinatario@email.com";
        String subject = "Assunto do Email";
        String message = "Olá, este é o corpo do e-mail.";

        emailService.enviarEmail(to, subject, message);

        verify(emailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage emailEnviado = messageCaptor.getValue();
        assertEquals("adopet@email.com.br", emailEnviado.getFrom());
        assertArrayEquals(new String[]{to}, emailEnviado.getTo());
        assertEquals(subject, emailEnviado.getSubject());
        assertEquals(message, emailEnviado.getText());
    }
}