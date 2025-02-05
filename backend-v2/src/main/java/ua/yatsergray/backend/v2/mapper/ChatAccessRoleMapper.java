package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.ChatAccessRoleDTO;
import ua.yatsergray.backend.v2.domain.entity.ChatAccessRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatAccessRoleMapper {

    ChatAccessRoleMapper INSTANCE = Mappers.getMapper(ChatAccessRoleMapper.class);

    ChatAccessRoleDTO mapToChatAccessRoleDTO(ChatAccessRole chatAccessRole);

    List<ChatAccessRoleDTO> mapAllToChatAccessRoleDTOList(List<ChatAccessRole> chatAccessRoles);
}
