package ua.yatsergray.backend.domain.dto.user;

import lombok.*;
import ua.yatsergray.backend.domain.dto.band.BandAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.StageRoleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private UUID id;
    private UUID imageFileId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<RoleDTO> roleDTOList = new ArrayList<>();
    private List<BandAccessRoleDTO> bandAccessRoleDTOList = new ArrayList<>();
    private List<StageRoleDTO> stageRoleDTOList = new ArrayList<>();
    private List<ChatAccessRoleDTO> chatAccessRoleDTOList = new ArrayList<>();
}
