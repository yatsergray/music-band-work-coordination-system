package ua.yatsergray.backend.domain.dto.band.editable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ua.yatsergray.backend.domain.type.band.EventCategoryType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventCategoryEditableDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Type is mandatory")
    private EventCategoryType type;
}
