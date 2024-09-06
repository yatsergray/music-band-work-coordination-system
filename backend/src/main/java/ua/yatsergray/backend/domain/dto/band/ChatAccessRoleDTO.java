package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.ChatAccessRoleType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatAccessRoleDTO {
    private UUID id;
    private String name;
    private ChatAccessRoleType type;
}
