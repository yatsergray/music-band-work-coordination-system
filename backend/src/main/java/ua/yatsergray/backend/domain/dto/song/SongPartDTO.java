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
public class SongPartDTO {
    private UUID id;
    private String text;
    private Integer typeNumber;
    private Integer measuresNumber;

    @JsonProperty("songPartCategory")
    private SongPartCategoryDTO songPartCategoryDTO;

    @JsonProperty("songPartKeyChords")
    private List<SongPartKeyChordDTO> songPartKeyChordDTOList = new ArrayList<>();
}
