package ua.yatsergray.backend.domain.dto.band;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventBandSongVersionDTO {
    private Integer sequenceNumber;

    @JsonProperty("bandSongVersion")
    private BandSongVersionDTO bandSongVersionDTO;
}
