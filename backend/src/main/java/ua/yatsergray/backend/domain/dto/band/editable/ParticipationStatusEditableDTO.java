package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.ParticipationStatusType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParticipationStatusEditableDTO {
    private String name;
    private ParticipationStatusType type;
}
