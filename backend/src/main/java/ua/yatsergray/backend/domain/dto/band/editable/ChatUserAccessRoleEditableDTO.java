package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatUserAccessRoleEditableDTO {

    @NotNull(message = "User id is mandatory")
    private UUID userId;

    @NotNull(message = "Chat access role id is mandatory")
    private UUID chatAccessRoleId;
}
