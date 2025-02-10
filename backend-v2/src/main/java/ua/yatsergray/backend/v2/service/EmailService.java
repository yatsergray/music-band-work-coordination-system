package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.request.SendEmailRequest;

public interface EmailService {

    void sendEmail(SendEmailRequest sendEmailRequest);
}
