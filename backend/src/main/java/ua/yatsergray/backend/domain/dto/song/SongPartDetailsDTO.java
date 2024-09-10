package ua.yatsergray.backend.domain.dto.song;

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
    private SongPartDTO songPartDTO;
}
