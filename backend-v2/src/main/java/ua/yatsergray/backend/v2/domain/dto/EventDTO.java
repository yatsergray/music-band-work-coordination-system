package ua.yatsergray.backend.v2.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private UUID musicBandId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("eventCategory")
    private EventCategoryDTO eventCategoryDTO;

    @JsonProperty("eventSongs")
    @Builder.Default
    private List<EventSongDTO> eventSongDTOList = new ArrayList<>();

    @JsonProperty("eventUsers")
    @Builder.Default
    private List<EventUserDTO> eventUserDTOList = new ArrayList<>();
}
