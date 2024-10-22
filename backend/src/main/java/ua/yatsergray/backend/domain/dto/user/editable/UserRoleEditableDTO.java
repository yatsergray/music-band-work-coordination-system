package ua.yatsergray.backend.domain.dto.user.editable;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRoleEditableDTO {

    @NotNull(message = "Role id is mandatory")
    private UUID roleId;
}
