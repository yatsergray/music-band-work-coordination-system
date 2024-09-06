package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartKeyChordEditableDTO {
    private Integer sequenceNumber;
    private UUID keyUUID;
    private UUID chordUUID;
    private UUID songPartUUID;
}
