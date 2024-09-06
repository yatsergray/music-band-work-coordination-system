package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;
import ua.yatsergray.backend.domain.type.band.EventCategoryType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventCategoryEditableDTO {
    private String name;
    private EventCategoryType type;
}
