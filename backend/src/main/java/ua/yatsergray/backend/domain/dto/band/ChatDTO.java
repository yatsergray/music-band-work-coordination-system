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
public class ChatDTO {
    private UUID id;
    private String imageFileURL;
    private String name;
    private List<MessageDTO> messageDTOList = new ArrayList<>();
    private List<UserDTO> userDTOList = new ArrayList<>();
}
