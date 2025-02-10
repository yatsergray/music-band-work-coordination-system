package ua.yatsergray.backend.v2.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventSongDTO {
    private UUID id;
    private Integer sequenceNumber;
    private UUID eventId;

    @JsonProperty("song")
    private SongDTO songDTO;
}
