package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartStructureDetailsEditableDTO {
    private Integer sequenceNumber;
    private Integer repeatNumber;
    private UUID songPartUUID;
    private UUID songStructureUUID;
}
