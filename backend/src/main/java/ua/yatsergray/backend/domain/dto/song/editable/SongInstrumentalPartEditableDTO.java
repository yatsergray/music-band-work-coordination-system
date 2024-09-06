package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongInstrumentalPartEditableDTO {
    private String audioFileName;
    private String videoFileName;
    private String tabFileName;
    private UUID songUUID;
    private UUID stageRoleUUID;
}
