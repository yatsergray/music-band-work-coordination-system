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
public class ChatDTO {
    private UUID id;
    private String name;
    private UUID musicBandId;

    @JsonProperty("chatUsers")
    @Builder.Default
    private List<ChatUserDTO> chatUserDTOList = new ArrayList<>();

    @JsonProperty("messages")
    @Builder.Default
    private List<MessageDTO> messageDTOList = new ArrayList<>();
}
