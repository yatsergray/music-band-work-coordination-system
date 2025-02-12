package ua.yatsergray.backend.v2.service;

public interface EmailService {

    void sendEmail(String recipientEmail, String subject, String body);

    void sendInvitationEmail(String recipientEmail, String invitationToken);
}
