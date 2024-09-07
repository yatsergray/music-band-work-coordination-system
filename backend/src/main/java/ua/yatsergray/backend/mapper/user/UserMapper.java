package ua.yatsergray.backend.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.dto.user.editable.UserEditableDTO;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.mapper.band.BandAccessRoleMapper;
import ua.yatsergray.backend.mapper.band.ChatAccessRoleMapper;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, BandAccessRoleMapper.class, StageRoleMapper.class, ChatAccessRoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageFileId", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "eventUsers", ignore = true)
    @Mapping(target = "bandUserAccessRoles", ignore = true)
    @Mapping(target = "bandUserStageRoles", ignore = true)
    @Mapping(target = "chatUserAccessRoles", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "bands", ignore = true)
    @Mapping(target = "chats", ignore = true)
    User mapToUser(UserEditableDTO userEditableDTO);

    @Mapping(source = "roles", target = "roleDTOList")
//    @Mapping(source = "bandUserAccessRoles", target = "bandUserAccessRoleDTOList") Will be formed in services layer
//    @Mapping(source = "bandUserStageRoles", target = "bandUserStageRoleDTOList")
//    @Mapping(source = "chatUserAccessRoles", target = "chatUserAccessRoleDTOList")
    UserDTO mapToUserDTO(User user);

    UserEditableDTO mapToUserEditableDTO(UserDTO userDTO);

    List<UserDTO> mapAllToUserDTOList(List<User> users);
}
