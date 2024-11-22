package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongCreateUpdateRequest {

    @Pattern(regexp = "^(http|https)://.*$", message = "Media URL must be a valid URL")
    private String mediaURL;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "BPM is mandatory")
    @Positive(message = "BPM must be a positive integer")
    private Integer bpm;

    @NotNull(message = "Key id is mandatory")
    private UUID keyId;

    @NotNull(message = "Artist id is mandatory")
    private UUID artistId;

    @NotNull(message = "Time signature id is mandatory")
    private UUID timeSignatureId;
}
