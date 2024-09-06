package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.EventCategoryType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventCategoryDTO {
    private UUID id;
    private String name;
    private EventCategoryType type;
}
