package ua.yatsergray.backend.domain.dto.song;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartDetailsDTO {
    private UUID id;
    private Integer sequenceNumber;
    private Integer repeatNumber;

    @JsonProperty("songPart")
    private SongPartDTO songPartDTO;
}
