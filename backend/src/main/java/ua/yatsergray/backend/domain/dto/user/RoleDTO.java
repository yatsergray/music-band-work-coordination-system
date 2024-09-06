package ua.yatsergray.backend.domain.dto.user;

import lombok.*;
import ua.yatsergray.backend.domain.type.user.RoleType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleDTO {
    private UUID id;
    private String name;
    private RoleType type;
}
