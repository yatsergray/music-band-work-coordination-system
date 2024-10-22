package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.ChatUserDTO;
import ua.yatsergray.backend.domain.dto.user.UserDTO;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;
import ua.yatsergray.backend.domain.entity.band.ChatUserAccessRole;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper
public interface ChatUserMapper {

    ChatUserMapper INSTANCE = Mappers.getMapper(ChatUserMapper.class);

    @Mapping(target = "userDTO", expression = "java(mapToUserDTO(user))")
    @Mapping(target = "chatAccessRoleDTOList", expression = "java(mapChatAccessRolesToChatAccessRoleDTOList(getChatAccessRolesFromChatAndUser(chat, user)))")
    ChatUserDTO mapToChatUserDTO(Chat chat, User user);

    default List<ChatUserDTO> mapAllToChatUserDTOList(Chat chat, List<User> users) {
        return users.stream()
                .map(user -> mapToChatUserDTO(chat, user))
                .toList();
    }

    default UserDTO mapToUserDTO(User user) {
        return UserMapper.INSTANCE.mapToUserDTO(user);
    }

    default List<ChatAccessRoleDTO> mapChatAccessRolesToChatAccessRoleDTOList(List<ChatAccessRole> chatAccessRoles) {
        return ChatAccessRoleMapper.INSTANCE.mapAllToChatAccessRoleDTOList(chatAccessRoles);
    }

    default List<ChatAccessRole> getChatAccessRolesFromChatAndUser(Chat chat, User user) {
        if (Objects.isNull(user)) {
            return Collections.emptyList();
        }

        return user.getChatUserAccessRoles().stream()
                .filter(streamChatUserAccessRole -> streamChatUserAccessRole.getChat().equals(chat))
                .map(ChatUserAccessRole::getChatAccessRole)
                .toList();
    }
}
