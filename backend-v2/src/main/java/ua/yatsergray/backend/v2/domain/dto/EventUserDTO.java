package ua.yatsergray.backend.v2.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventUserDTO {
    private UUID id;
    private UUID eventId;

    @JsonProperty("musicBandUser")
    private MusicBandUserDTO musicBandUserDTO;

    @JsonProperty("stageRole")
    private StageRoleDTO stageRoleDTO;

    @JsonProperty("participationStatus")
    private ParticipationStatusDTO participationStatusDTO;
}
