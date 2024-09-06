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
public class SongStructureDTO {
    private UUID id;
    private List<SongPartStructureDetailsDTO> songPartStructureDetailsDTOList = new ArrayList<>();
}
