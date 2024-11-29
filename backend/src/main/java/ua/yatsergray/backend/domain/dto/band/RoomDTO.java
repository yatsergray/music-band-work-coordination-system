package ua.yatsergray.backend.domain.dto.band;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomDTO {
    private UUID id;
    private String name;
}
