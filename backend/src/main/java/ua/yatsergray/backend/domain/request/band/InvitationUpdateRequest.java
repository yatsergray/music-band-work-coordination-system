package ua.yatsergray.backend.domain.request.band;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvitationUpdateRequest {

    @NotNull(message = "Participation status id is mandatory")
    private UUID participationStatusId;
}
