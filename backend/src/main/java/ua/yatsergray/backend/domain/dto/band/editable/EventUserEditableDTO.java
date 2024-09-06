package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventUserEditableDTO {
    private UUID userUUID;
    private UUID eventUUID;
    private UUID stageRoleUUID;
    private UUID participationStatusUUID;
}
