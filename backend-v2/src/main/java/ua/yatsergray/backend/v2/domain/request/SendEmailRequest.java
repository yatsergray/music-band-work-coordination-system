package ua.yatsergray.backend.v2.domain.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SendEmailRequest {
    private String recipientEmail;
    private String subject;
    private String body;
}
