package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SendEmailRequest {

    @NotBlank(message = "Recipient email is mandatory")
    @Email(message = "Recipient email should be valid")
    private String recipientEmail;

    @NotBlank(message = "Subject is mandatory")
    private String subject;

    @NotBlank(message = "Body is mandatory")
    private String body;
}
