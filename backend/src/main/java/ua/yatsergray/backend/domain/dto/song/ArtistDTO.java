package ua.yatsergray.backend.domain.dto.song;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArtistDTO {
    private UUID id;
    private String imageFileURL;
    private String name;
}
