package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChordEditableDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
