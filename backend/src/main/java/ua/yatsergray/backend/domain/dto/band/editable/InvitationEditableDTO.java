package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvitationEditableDTO {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Band id is mandatory")
    private UUID bandId;

    @NotNull(message = "Participation status id is mandatory")
    private UUID participationStatusId;
}
