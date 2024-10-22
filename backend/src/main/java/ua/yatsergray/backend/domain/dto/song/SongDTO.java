package ua.yatsergray.backend.domain.dto.song;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("key")
    private KeyDTO keyDTO;

    @JsonProperty("artist")
    private ArtistDTO artistDTO;

    @JsonProperty("timeSignature")
    private TimeSignatureDTO timeSignatureDTO;

    @JsonProperty("songParts")
    private List<SongPartDTO> songPartDTOList = new ArrayList<>();

    @JsonProperty("songPartDetails")
    private List<SongPartDetailsDTO> songPartDetailsDTOList = new ArrayList<>();

    @JsonProperty("songInstrumentalParts")
    private List<SongInstrumentalPartDTO> songInstrumentalPartDTOList = new ArrayList<>();

    @JsonProperty("keys")
    private List<KeyDTO> keyDTOList = new ArrayList<>();
}
