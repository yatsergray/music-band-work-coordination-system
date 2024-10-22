package ua.yatsergray.backend.domain.dto.song;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongInstrumentalPartDTO {
    private UUID id;
    private UUID audioFileId;
    private UUID videoFileId;
    private UUID tabFileId;

    @JsonProperty("stageRole")
    private StageRoleDTO stageRoleDTO;
}
