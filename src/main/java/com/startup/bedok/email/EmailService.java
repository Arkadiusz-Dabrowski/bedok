package com.startup.bedok.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendNewPassword(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bedokpolska@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendPaymentLink(
            String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bedokpolska@gmail.com");
        message.setTo(to);
        message.setSubject("Payment Link for your new reservation");
        message.setText(text);
        emailSender.send(message);
    }
}
