package ua.yatsergray.backend.v2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private static String senderEmail;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String recipientEmail, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendInvitationEmail(String recipientEmail, String invitationToken) {
        String subject = "Invitation to join the Music band";
        String body = String.format("Click the link to join the Music band: http://localhost:8080/api/v1/music-bands/join?invitationToken=%s", invitationToken);

        sendEmail(recipientEmail, subject, body);
    }
}
