package ua.yatsergray.backend.domain.dto.song;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TimeSignatureDTO {
    private UUID id;
    private Integer beats;
    private Integer duration;
}
