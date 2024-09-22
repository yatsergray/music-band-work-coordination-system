package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventBandSongVersionEditableDTO {

    @NotNull(message = "Sequence number is mandatory")
    @Positive(message = "Sequence number must be a positive integer")
    private Integer sequenceNumber;

    @NotNull(message = "Event id is mandatory")
    private UUID eventId;

    @NotNull(message = "Band song version id is mandatory")
    private UUID bandSongVersionId;
}
