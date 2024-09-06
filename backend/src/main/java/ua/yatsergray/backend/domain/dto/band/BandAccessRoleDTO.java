package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.BandAccessRoleType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandAccessRoleDTO {
    private UUID id;
    private String name;
    private BandAccessRoleType type;
}
