package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvitationEditableDTO {
    private String email;
    private String token;
    private UUID bandUUID;
    private UUID participationStatusUUID;
}
