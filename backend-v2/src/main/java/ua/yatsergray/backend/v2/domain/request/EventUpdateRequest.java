package ua.yatsergray.backend.v2.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventUpdateRequest {

    @NotNull(message = "Date is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull(message = "Start time is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "End time is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull(message = "Event category id is mandatory")
    private UUID eventCategoryId;
}
