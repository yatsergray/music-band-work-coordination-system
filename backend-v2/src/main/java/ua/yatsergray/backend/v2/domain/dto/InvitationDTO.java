package ua.yatsergray.backend.v2.domain.dto;

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
    private UUID musicBandId;

    @JsonProperty("participationStatus")
    private ParticipationStatusDTO participationStatusDTO;
}
