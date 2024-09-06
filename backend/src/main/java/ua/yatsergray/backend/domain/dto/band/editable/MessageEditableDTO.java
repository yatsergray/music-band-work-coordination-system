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
public class MessageEditableDTO {
    private String text;
    private LocalDate date;
    private LocalTime time;
    private Boolean edited;
    private UUID chatUUID;
    private UUID userUUID;
}
