package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.ChatUserDTO;
import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.entity.band.Message;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "chatUserDTO", expression = "java(mapMessageToChatUserDTO(message))")
    MessageDTO mapToMessageDTO(Message message);

    List<MessageDTO> mapAllToMessageDTOList(List<Message> messages);

    default ChatUserDTO mapMessageToChatUserDTO(Message message) {
        if (Objects.isNull(message.getUser())) {
            return null;
        }

        return ChatUserMapper.INSTANCE.mapToChatUserDTO(message.getChat(), message.getUser());
    }
}
