package ua.yatsergray.backend.domain.dto.song.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongEditableDTO {
    private String imageFileName;
    private String audioFileName;
    private String mediaURL;
    private String name;
    private Integer bpm;
    private UUID keyUUID;
    private UUID artistUUID;
    private UUID timeSignatureUUID;
}
