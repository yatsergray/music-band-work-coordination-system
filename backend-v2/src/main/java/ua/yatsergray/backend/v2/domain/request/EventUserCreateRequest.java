package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventUserCreateRequest {

    @NotNull(message = "Event id is mandatory")
    private UUID eventId;

    @NotNull(message = "User id is mandatory")
    private UUID userId;

    @NotNull(message = "Stage role id is mandatory")
    private UUID stageRoleId;

    @NotNull(message = "Participation status id is mandatory")
    private UUID participationStatusId;
}
