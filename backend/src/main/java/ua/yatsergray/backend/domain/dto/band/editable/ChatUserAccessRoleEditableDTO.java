package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatUserAccessRoleEditableDTO {
    private UUID chatUUID;
    private UUID userUUID;
    private UUID chatAccessRoleUUID;
}
