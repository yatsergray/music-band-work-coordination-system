package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartDetailsEditableDTO {

    @NotNull(message = "Sequence number is mandatory")
    @Positive(message = "Sequence number must be a positive integer")
    private Integer sequenceNumber;

    @NotNull(message = "Repeat number is mandatory")
    @Positive(message = "Repeat number must be a positive integer")
    private Integer repeatNumber;

    @NotNull(message = "Song part id is mandatory")
    private UUID songPartId;
    private UUID songId;
    private UUID bandSongVersionId;
}
