package ua.yatsergray.backend.domain.dto.band;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventBandSongVersionDTO {
    private Integer sequenceNumber;
    private BandSongVersionDTO bandSongVersionDTO;
}
