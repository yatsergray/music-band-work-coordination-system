package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongDTO {
    private UUID id;
    private String name;
    private String artistName;
    private String youtubeId;
    private UUID musicBandId;
}
