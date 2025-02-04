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
public class MusicBandUserDTO {

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("musicBandAccessRoles")
    private List<MusicBandAccessRoleDTO> musicBandAccessRoleDTOList = new ArrayList<>();

    @JsonProperty("stageRoles")
    private List<StageRoleDTO> stageRoleDTOList = new ArrayList<>();
}
