package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.BandAccessRoleType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandAccessRoleEditableDTO {
    private String name;
    private BandAccessRoleType type;
}
