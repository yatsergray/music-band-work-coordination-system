package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.dto.song.KeyDTO;
import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.SongPartDetailsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandSongVersionDTO {
    private UUID id;
    private UUID audioFileId;
    private KeyDTO keyDTO;
    private SongDTO songDTO;
    private List<SongPartDetailsDTO> songPartDetailsDTOList = new ArrayList<>();
}
