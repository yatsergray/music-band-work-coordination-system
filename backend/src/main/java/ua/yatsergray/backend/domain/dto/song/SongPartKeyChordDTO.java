package ua.yatsergray.backend.domain.dto.song;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartKeyChordDTO {
    private UUID id;
    private Integer sequenceNumber;
    private KeyDTO keyDTO;
    private ChordDTO chordDTO;
}
