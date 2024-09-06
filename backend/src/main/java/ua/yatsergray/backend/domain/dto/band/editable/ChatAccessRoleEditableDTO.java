package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.ChatAccessRoleType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatAccessRoleEditableDTO {
    private String name;
    private ChatAccessRoleType type;
}
