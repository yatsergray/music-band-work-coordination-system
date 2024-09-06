package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.dto.band.editable.MessageEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Message;
import ua.yatsergray.backend.mapper.user.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chat", ignore = true)
    @Mapping(target = "user", ignore = true)
    Message mapToMessage(MessageEditableDTO messageEditableDTO);

    @Mapping(source = "user", target = "userDTO")
    MessageDTO mapToMessageDTO(Message message);

    @Mapping(source = "chat.id", target = "chatUUID")
    @Mapping(source = "user.id", target = "userUUID")
    MessageEditableDTO mapToMessageEditableDTO(Message message);

    List<MessageDTO> mapAllToMessageDTOList(List<Message> messages);
}
