package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventSongCreateRequest {

    @NotNull(message = "Sequence number is mandatory")
    @Positive(message = "Sequence number must be a positive integer")
    private Integer sequenceNumber;

    @NotNull(message = "Event id is mandatory")
    private UUID eventId;

    @NotNull(message = "Band song version id is mandatory")
    private UUID songId;
}
