package ua.yatsergray.backend.domain.dto.band;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ua.yatsergray.backend.domain.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BandUserDTO {

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("bandAccessRoles")
    private List<BandAccessRoleDTO> bandAccessRoleDTOList = new ArrayList<>();

    @JsonProperty("stageRoles")
    private List<StageRoleDTO> stageRoleDTOList = new ArrayList<>();
}
