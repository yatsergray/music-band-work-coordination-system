package ua.yatsergray.backend.v2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.request.SendEmailRequest;
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
    public void sendEmail(SendEmailRequest sendEmailRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(sendEmailRequest.getRecipientEmail());
        mailMessage.setSubject(sendEmailRequest.getSubject());
        mailMessage.setText(sendEmailRequest.getBody());

        javaMailSender.send(mailMessage);
    }
}
