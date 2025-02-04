package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;
import ua.yatsergray.backend.v2.domain.type.StageRoleType;

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
