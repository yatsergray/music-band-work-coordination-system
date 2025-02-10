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
public class MusicBandUserDTO {
    private UUID musicBandId;

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("musicBandAccessRoles")
    @Builder.Default
    private List<MusicBandAccessRoleDTO> musicBandAccessRoleDTOList = new ArrayList<>();

    @JsonProperty("stageRoles")
    @Builder.Default
    private List<StageRoleDTO> stageRoleDTOList = new ArrayList<>();
}
