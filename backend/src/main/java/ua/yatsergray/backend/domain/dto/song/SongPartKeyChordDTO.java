package ua.yatsergray.backend.domain.dto.song;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("key")
    private KeyDTO keyDTO;

    @JsonProperty("chord")
    private ChordDTO chordDTO;
}
