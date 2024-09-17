package ua.yatsergray.backend.domain.dto.song;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongDTO {
    private UUID id;
    private UUID imageFileId;
    private UUID audioFileId;
    private String mediaURL;
    private String name;
    private Integer bpm;
    private KeyDTO keyDTO;
    private ArtistDTO artistDTO;
    private TimeSignatureDTO timeSignatureDTO;
    private List<SongPartDTO> songPartDTOList = new ArrayList<>();
    private List<SongPartDetailsDTO> songPartDetailsDTOList = new ArrayList<>();
    private List<SongInstrumentalPartDTO> songInstrumentalPartDTOList = new ArrayList<>();
    private List<KeyDTO> keyDTOList = new ArrayList<>();
}
