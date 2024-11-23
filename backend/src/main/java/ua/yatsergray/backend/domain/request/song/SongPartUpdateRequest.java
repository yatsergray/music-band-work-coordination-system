package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongPartUpdateRequest {

    @NotBlank(message = "Text is mandatory")
    private String text;

    @NotNull(message = "Type number is mandatory")
    @Positive(message = "Type number must be a positive integer")
    private Integer typeNumber;

    @Positive(message = "Measures number must be a positive integer")
    private Integer measuresNumber;

    @NotNull(message = "Song part category id is mandatory")
    private UUID songPartCategoryId;
}
