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
public class SongPartDTO {
    private UUID id;
    private String text;
    private Integer typeNumber;
    private Integer measuresNumber;
    private SongPartCategoryDTO songPartCategoryDTO;
    private List<SongPartKeyChordDTO> songPartKeyChordDTOList = new ArrayList<>();
}
