package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandUserAccessRoleEditableDTO {

    @NotNull(message = "Band access role id is mandatory")
    private UUID bandAccessRoleId;
}
