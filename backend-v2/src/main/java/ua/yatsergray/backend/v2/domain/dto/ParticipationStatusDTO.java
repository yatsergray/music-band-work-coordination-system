package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;

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
