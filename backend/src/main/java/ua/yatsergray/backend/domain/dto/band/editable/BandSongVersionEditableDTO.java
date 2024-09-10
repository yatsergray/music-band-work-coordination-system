package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandSongVersionEditableDTO {
    private UUID keyUUID;
    private UUID bandUUID;
    private UUID songUUID;
}
