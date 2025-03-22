package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MusicBandUserCreateRequest {

    @NotNull(message = "Music band id is mandatory")
    private UUID musicBandId;

    @NotNull(message = "User id is mandatory")
    private UUID userId;
}
