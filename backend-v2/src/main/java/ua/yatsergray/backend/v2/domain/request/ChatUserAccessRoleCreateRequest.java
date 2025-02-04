package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatUserAccessRoleCreateRequest {

    @NotNull(message = "Chat access role id is mandatory")
    private UUID chatAccessRoleId;
}
