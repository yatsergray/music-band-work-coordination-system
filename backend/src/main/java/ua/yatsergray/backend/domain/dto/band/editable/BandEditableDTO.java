package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandEditableDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
