package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ua.yatsergray.backend.domain.type.song.SongPartCategoryType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartCategoryEditableDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Type is mandatory")
    private SongPartCategoryType type;
}
