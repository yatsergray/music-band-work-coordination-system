package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.ChatDTO;
import ua.yatsergray.backend.v2.domain.dto.ChatUserDTO;
import ua.yatsergray.backend.v2.domain.entity.Chat;
import ua.yatsergray.backend.v2.domain.entity.ChatUserAccessRole;
import ua.yatsergray.backend.v2.domain.entity.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MessageMapper.class})
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(source = "musicBand.id", target = "musicBandId")
    @Mapping(target = "chatUserDTOList", expression = "java(mapChatToChatUserDTOList(chat))")
    @Mapping(source = "messages", target = "messageDTOList")
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
