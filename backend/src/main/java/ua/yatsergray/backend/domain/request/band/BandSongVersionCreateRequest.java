package ua.yatsergray.backend.domain.request.band;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandSongVersionCreateRequest {

    @NotNull(message = "Key id is mandatory")
    private UUID keyId;

    @NotNull(message = "Band id is mandatory")
    private UUID bandId;

    @NotNull(message = "Song id is mandatory")
    private UUID songId;
}
