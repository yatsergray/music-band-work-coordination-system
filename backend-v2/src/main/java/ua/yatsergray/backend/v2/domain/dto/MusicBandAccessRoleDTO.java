package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;
import ua.yatsergray.backend.v2.domain.type.MusicBandAccessRoleType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MusicBandAccessRoleDTO {
    private UUID id;
    private String name;
    private MusicBandAccessRoleType type;
}
