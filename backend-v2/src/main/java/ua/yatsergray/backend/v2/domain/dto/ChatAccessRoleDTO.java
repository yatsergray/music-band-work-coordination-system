package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;
import ua.yatsergray.backend.v2.domain.type.ChatAccessRoleType;

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
