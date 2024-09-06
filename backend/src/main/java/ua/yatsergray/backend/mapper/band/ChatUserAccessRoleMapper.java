package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.ChatUserAccessRole;

@Mapper(componentModel = "spring")
public interface ChatUserAccessRoleMapper {

    ChatUserAccessRoleMapper INSTANCE = Mappers.getMapper(ChatUserAccessRoleMapper.class);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "chat", ignore = true)
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "accessRole", ignore = true)
//    NoSuchChatUserAccessRoleException mapToChatUserAccessRole(ChatUserAccessRoleEditableDTO chatUserAccessRoleEditableDTO);

    @Mapping(source = "chat.id", target = "chatUUID")
    @Mapping(source = "user.id", target = "userUUID")
    @Mapping(source = "chatAccessRole.id", target = "chatAccessRoleUUID")
    ChatUserAccessRoleEditableDTO mapToChatUserAccessRoleEditableDTO(ChatUserAccessRole chatUserAccessRole);
}
