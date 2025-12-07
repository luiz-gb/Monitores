package org.example.domain;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Mensageiro {
    public static void enviarEmail(String email, String titulo, String mensagem) throws MessagingException {
        // configs
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Credenciais
        final String remetente = "projeto.monitoria.java@gmail.com";
        final String senha = "fzwe prmu arxg mlgj";

        // Cria uma sessão
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            // Monta o email e envia para o destinatário
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject(titulo);
            message.setText(mensagem);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new MessagingException("Erro ao enviar email!");
        }
    }
}
