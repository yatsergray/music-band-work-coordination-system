package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventBandSongVersionEditableDTO {
    private Integer sequenceNumber;
    private UUID eventUUID;
    private UUID bandSongVersionUUID;
}
