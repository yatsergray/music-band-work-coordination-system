package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandEditableDTO {
    private String imageFileName;
    private String name;
}
