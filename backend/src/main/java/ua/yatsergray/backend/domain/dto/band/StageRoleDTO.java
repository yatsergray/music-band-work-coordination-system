package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.StageRoleType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StageRoleDTO {
    private UUID id;
    private String name;
    private StageRoleType type;
}
