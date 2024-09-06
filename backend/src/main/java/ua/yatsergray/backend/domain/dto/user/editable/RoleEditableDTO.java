package ua.yatsergray.backend.domain.dto.user.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.user.RoleType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleEditableDTO {
    private String name;
    private RoleType type;
}
