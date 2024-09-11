package ua.yatsergray.backend.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.mapper.band.BandAccessRoleMapper;
import ua.yatsergray.backend.mapper.band.ChatAccessRoleMapper;
import ua.yatsergray.backend.mapper.band.StageRoleMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, BandAccessRoleMapper.class, StageRoleMapper.class, ChatAccessRoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roleDTOList")
//    @Mapping(source = "bandUserAccessRoles", target = "bandUserAccessRoleDTOList") Will be formed in services layer
//    @Mapping(source = "bandUserStageRoles", target = "bandUserStageRoleDTOList")
//    @Mapping(source = "chatUserAccessRoles", target = "chatUserAccessRoleDTOList")
    UserDTO mapToUserDTO(User user);

    List<UserDTO> mapAllToUserDTOList(List<User> users);
}
