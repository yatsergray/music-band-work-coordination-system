package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventEditableDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID bandUUID;
    private UUID eventCategoryUUID;
}
