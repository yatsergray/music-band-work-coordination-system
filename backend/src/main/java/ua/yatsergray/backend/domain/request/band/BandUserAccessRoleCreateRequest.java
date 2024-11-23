package ua.yatsergray.backend.domain.request.band;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandUserAccessRoleCreateRequest {

    @NotNull(message = "Band access role id is mandatory")
    private UUID bandAccessRoleId;
}
