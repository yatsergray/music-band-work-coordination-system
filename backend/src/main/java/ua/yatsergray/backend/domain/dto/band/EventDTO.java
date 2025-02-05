package ua.yatsergray.backend.domain.dto.band;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventDTO {
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @JsonProperty("eventCategory")
    private EventCategoryDTO eventCategoryDTO;

    @JsonProperty("room")
    private RoomDTO roomDTO;

    @JsonProperty("eventStatus")
    private EventStatusDTO eventStatusDTO;

    @JsonProperty("eventUsers")
    private List<EventUserDTO> eventUserDTOList = new ArrayList<>();

    @JsonProperty("eventBandSongVersions")
    private List<EventBandSongVersionDTO> eventBandSongVersionDTOList = new ArrayList<>();
}
