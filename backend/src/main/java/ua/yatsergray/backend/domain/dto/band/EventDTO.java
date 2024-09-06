package ua.yatsergray.backend.domain.dto.band;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventDTO {
    private UUID id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private EventCategoryDTO eventCategoryDTO;
    private List<EventUserDTO> eventUserDTOList = new ArrayList<>();
    private List<EventBandSongVersionDTO> eventBandSongVersionDTOList = new ArrayList<>();
}
