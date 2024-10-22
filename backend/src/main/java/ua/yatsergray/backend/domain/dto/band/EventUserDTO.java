package ua.yatsergray.backend.domain.dto.band;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ua.yatsergray.backend.domain.dto.user.UserDTO;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventUserDTO {
    private UUID id;

    @JsonProperty("bandUser")
    private BandUserDTO bandUserDTO;

    @JsonProperty("stageRole")
    private StageRoleDTO stageRoleDTO;

    @JsonProperty("participationStatus")
    private ParticipationStatusDTO participationStatusDTO;
}
