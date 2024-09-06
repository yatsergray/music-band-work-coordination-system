package ua.yatsergray.backend.domain.dto.band;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvitationDTO {
    private UUID id;
    private String email;
    private String token;
    private ParticipationStatusDTO participationStatusDTO;
}
