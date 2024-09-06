package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartEditableDTO {
    private String text;
    private Integer typeNumber;
    private Integer measuresNumber;
    private UUID songUUID;
    private UUID songPartCategoryUUID;
}
