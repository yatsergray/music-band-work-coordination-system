package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongCreateUpdateRequest {

    @NotBlank(message = "YouTube id s mandatory")
    private String youTubeId;

    @NotNull(message = "Band id is mandatory")
    private UUID bandId;
}
