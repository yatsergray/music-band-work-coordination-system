package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SongPartKeyChordEditableDTO {

    @NotNull(message = "Sequence number is mandatory")
    @Positive(message = "Sequence number must be a positive integer")
    private Integer sequenceNumber;

    @NotNull(message = "Key id is mandatory")
    private UUID keyId;

    @NotNull(message = "Chord id is mandatory")
    private UUID chordId;

    @NotNull(message = "Song part id is mandatory")
    private UUID songPartId;
}
