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
public class ChatDTO {
    private UUID id;
    private UUID imageFileId;
    private String name;

    @JsonProperty("messages")
    private List<MessageDTO> messageDTOList = new ArrayList<>();

    @JsonProperty("chatUsers")
    private List<ChatUserDTO> chatUserDTOList = new ArrayList<>();
}
