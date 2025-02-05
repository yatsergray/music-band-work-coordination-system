package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.BandAccessRoleType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MusicBandAccessRoleDTO {
    private UUID id;
    private String name;
    private BandAccessRoleType type;
}
