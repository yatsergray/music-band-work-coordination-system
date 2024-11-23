package ua.yatsergray.backend.domain.request.band;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandUserCreateRequest {

    @NotNull(message = "User id is mandatory")
    private UUID userId;
}
