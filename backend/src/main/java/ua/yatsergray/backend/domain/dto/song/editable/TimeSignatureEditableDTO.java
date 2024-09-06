package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TimeSignatureEditableDTO {
    private Integer beats;
    private Integer duration;
}
