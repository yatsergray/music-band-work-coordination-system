package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatAccessRoleMapper {

    ChatAccessRoleMapper INSTANCE = Mappers.getMapper(ChatAccessRoleMapper.class);

    ChatAccessRoleDTO mapToChatAccessRoleDTO(ChatAccessRole chatAccessRole);

    List<ChatAccessRoleDTO> mapAllToChatAccessRoleDTOList(List<ChatAccessRole> chatAccessRoles);
}
