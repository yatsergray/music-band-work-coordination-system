package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArtistEditableDTO {
    private String imageFileName;
    private String name;
}
