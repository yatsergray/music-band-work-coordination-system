package ua.yatsergray.backend.domain.dto.song;

import lombok.*;
import ua.yatsergray.backend.domain.type.song.SongPartCategoryType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartCategoryDTO {
    private UUID id;
    private String name;
    private SongPartCategoryType type;
}
