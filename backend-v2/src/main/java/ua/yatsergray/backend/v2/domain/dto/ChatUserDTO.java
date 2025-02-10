package ua.yatsergray.backend.v2.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatUserDTO {

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("chatAccessRoles")
    @Builder.Default
    private List<ChatAccessRoleDTO> chatAccessRoleDTOList = new ArrayList<>();
}
