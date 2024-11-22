package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartDetailsUpdateRequest {

    @NotNull(message = "Sequence number is mandatory")
    @Positive(message = "Sequence number must be a positive integer")
    private Integer sequenceNumber;

    @NotNull(message = "Repeat number is mandatory")
    @Positive(message = "Repeat number must be a positive integer")
    private Integer repeatNumber;
}
