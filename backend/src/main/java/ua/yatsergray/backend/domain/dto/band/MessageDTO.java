package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.dto.user.UserDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageDTO {
    private UUID id;
    private String text;
    private LocalDate date;
    private LocalTime time;
    private Boolean edited;
    private UserDTO userDTO;
}
