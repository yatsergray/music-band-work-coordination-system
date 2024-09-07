package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MessageMapper.class, UserMapper.class})
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageFileId", ignore = true)
    @Mapping(target = "band", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "chatUserAccessRoles", ignore = true)
    @Mapping(target = "users", ignore = true)
    Chat mapToChat(ChatEditableDTO chatEditableDTO);

    @Mapping(source = "messages", target = "messageDTOList")
    @Mapping(source = "users", target = "userDTOList")
    ChatDTO mapToChatDTO(Chat chat);

    @Mapping(source = "band.id", target = "bandUUID")
    ChatEditableDTO mapToChatEditableDTO(Chat chat);

    List<ChatDTO> mapAllToChatDTOList(List<Chat> chats);
}
