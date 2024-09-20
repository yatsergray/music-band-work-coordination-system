package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TimeSignatureEditableDTO {

    @NotNull(message = "Beats is mandatory")
    @Positive(message = "Beats must be a positive integer")
    private Integer beats;

    @NotNull(message = "Duration is mandatory")
    @Positive(message = "Duration must be a positive integer")
    private Integer duration;
}
