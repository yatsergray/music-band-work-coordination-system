package ua.yatsergray.backend.v2.domain.dto;

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
public class MusicBandDTO {
    private UUID id;
    private String name;

    @JsonProperty("invitations")
    @Builder.Default
    private List<InvitationDTO> invitationDTOList = new ArrayList<>();

    @JsonProperty("musicBandUsers")
    @Builder.Default
    private List<MusicBandUserDTO> musicBandUserDTOList = new ArrayList<>();

    @JsonProperty("chats")
    @Builder.Default
    private List<ChatDTO> chatDTOList = new ArrayList<>();

    @JsonProperty("songs")
    @Builder.Default
    private List<SongDTO> songDTOList = new ArrayList<>();

    @JsonProperty("events")
    @Builder.Default
    private List<EventDTO> eventDTOList = new ArrayList<>();
}
