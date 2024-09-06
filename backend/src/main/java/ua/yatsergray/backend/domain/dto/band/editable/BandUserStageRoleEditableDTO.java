package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandUserStageRoleEditableDTO {
    private UUID bandUUID;
    private UUID userUUID;
    private UUID stageRoleUUID;
}
