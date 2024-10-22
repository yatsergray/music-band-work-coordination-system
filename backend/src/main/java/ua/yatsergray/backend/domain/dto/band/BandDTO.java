package ua.yatsergray.backend.domain.dto.band;

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
public class BandDTO {
    private UUID id;
    private UUID imageFileId;
    private String name;

    @JsonProperty("chats")
    private List<ChatDTO> chatDTOList = new ArrayList<>();

    @JsonProperty("events")
    private List<EventDTO> eventDTOList = new ArrayList<>();

    @JsonProperty("invitations")
    private List<InvitationDTO> invitationDTOList = new ArrayList<>();

    @JsonProperty("bandSongVersions")
    private List<BandSongVersionDTO> bandSongVersionDTOList = new ArrayList<>();

    @JsonProperty("bandUsers")
    private List<BandUserDTO> bandUserDTOList = new ArrayList<>();
}
