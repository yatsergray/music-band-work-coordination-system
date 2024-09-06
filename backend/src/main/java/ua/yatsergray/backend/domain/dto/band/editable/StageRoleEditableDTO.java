package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.StageRoleType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StageRoleEditableDTO {
    private String name;
    private StageRoleType type;
}
