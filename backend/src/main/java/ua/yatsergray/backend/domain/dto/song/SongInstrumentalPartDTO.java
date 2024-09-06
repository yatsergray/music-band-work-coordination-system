package ua.yatsergray.backend.domain.dto.song;

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
    private String audioFileURL;
    private String videoFileURL;
    private String tabFileURL;
    private StageRoleDTO stageRoleDTO;
}
