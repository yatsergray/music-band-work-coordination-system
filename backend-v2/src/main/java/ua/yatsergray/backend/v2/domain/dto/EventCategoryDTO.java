package ua.yatsergray.backend.v2.domain.dto;

import lombok.*;
import ua.yatsergray.backend.v2.domain.type.EventCategoryType;

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
