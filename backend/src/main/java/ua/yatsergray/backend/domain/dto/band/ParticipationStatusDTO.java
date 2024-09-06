package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.ParticipationStatusType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParticipationStatusDTO {
    private UUID id;
    private String name;
    private ParticipationStatusType type;
}
