package ua.yatsergray.backend.domain.dto.band;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("participationStatus")
    private ParticipationStatusDTO participationStatusDTO;
}
