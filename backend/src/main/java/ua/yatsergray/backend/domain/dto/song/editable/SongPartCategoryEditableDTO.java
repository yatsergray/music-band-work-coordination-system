package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.song.SongPartCategoryType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartCategoryEditableDTO {
    private String name;
    private SongPartCategoryType type;
}
