package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongInstrumentalPartCreateRequest {

    @NotNull(message = "Song id is mandatory")
    private UUID songId;

    @NotNull(message = "Stage role id is mandatory")
    private UUID stageRoleId;
}
