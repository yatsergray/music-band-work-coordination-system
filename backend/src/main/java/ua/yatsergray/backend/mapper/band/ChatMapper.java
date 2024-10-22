package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.ChatUserDTO;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.band.ChatUserAccessRole;
import ua.yatsergray.backend.domain.entity.user.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MessageMapper.class})
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(source = "messages", target = "messageDTOList")
    @Mapping(target = "chatUserDTOList", expression = "java(mapChatToChatUserDTOList(chat))")
    ChatDTO mapToChatDTO(Chat chat);

    List<ChatDTO> mapAllToChatDTOList(List<Chat> chats);

    default List<ChatUserDTO> mapChatToChatUserDTOList(Chat chat) {
        return ChatUserMapper.INSTANCE.mapAllToChatUserDTOList(chat, mapChatUserAccessRolesToUsers(chat.getChatUserAccessRoles().stream().toList()));
    }

    default List<User> mapChatUserAccessRolesToUsers(List<ChatUserAccessRole> chatUserAccessRoles) {
        return chatUserAccessRoles.stream()
                .map(ChatUserAccessRole::getUser)
                .distinct()
                .toList();
    }
}
