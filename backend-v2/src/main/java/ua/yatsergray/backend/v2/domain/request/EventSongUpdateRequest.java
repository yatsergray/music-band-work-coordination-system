package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventSongUpdateRequest {

    @NotNull(message = "Sequence number is mandatory")
    @Positive(message = "Sequence number must be a positive integer")
    private Integer sequenceNumber;
}
