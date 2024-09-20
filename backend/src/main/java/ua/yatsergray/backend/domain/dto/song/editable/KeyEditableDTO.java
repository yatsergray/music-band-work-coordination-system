package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KeyEditableDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
