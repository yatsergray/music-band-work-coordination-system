package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.dto.band.editable.MessageEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.exception.band.NoSuchMessageException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    MessageDTO addMessage(MessageEditableDTO messageEditableDTO) throws NoSuchChatException, NoSuchUserException;

    Optional<MessageDTO> getMessageById(UUID id);

    List<MessageDTO> getAllMessages();

    MessageDTO modifyMessageById(UUID id, MessageEditableDTO messageEditableDTO) throws NoSuchMessageException, NoSuchChatException, NoSuchUserException;

    void removeMessageById(UUID id) throws NoSuchMessageException;
}
