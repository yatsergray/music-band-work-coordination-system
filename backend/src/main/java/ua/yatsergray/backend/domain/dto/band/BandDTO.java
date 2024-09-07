package ua.yatsergray.backend.domain.dto.band;

import lombok.*;
import ua.yatsergray.backend.domain.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandDTO {
    private UUID id;
    private UUID imageFileId;
    private String name;
    private List<ChatDTO> chatDTOList = new ArrayList<>();
    private List<EventDTO> eventDTOList = new ArrayList<>();
    private List<InvitationDTO> invitationDTOList = new ArrayList<>();
    private List<BandSongVersionDTO> bandSongVersionDTOList = new ArrayList<>();
    private List<UserDTO> userDTOList = new ArrayList<>();
}
