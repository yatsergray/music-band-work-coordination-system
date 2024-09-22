package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandUserStageRoleEditableDTO {

    @NotNull(message = "Band id is mandatory")
    private UUID bandId;

    @NotNull(message = "User id is mandatory")
    private UUID userId;

    @NotNull(message = "Stage role id is mandatory")
    private UUID stageRoleId;
}
