package ua.yatsergray.backend.domain.dto.band;

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
    private UserDTO userDTO;
    private StageRoleDTO stageRoleDTO;
    private ParticipationStatusDTO participationStatusDTO;
}
