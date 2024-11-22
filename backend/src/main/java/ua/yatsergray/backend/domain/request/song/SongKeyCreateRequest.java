package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongKeyCreateRequest {

    @NotNull(message = "Key id is mandatory")
    private UUID keyId;
}
