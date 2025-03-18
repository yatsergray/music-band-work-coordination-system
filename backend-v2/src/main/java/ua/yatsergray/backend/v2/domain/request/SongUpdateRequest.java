package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongUpdateRequest {

    @NotBlank(message = "YouTube id s mandatory")
    private String youTubeId;
}
