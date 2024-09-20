package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.dto.band.editable.MessageEditableDTO;
import ua.yatsergray.backend.exception.band.MessageConflictException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.exception.band.NoSuchMessageException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    MessageDTO addMessage(MessageEditableDTO messageEditableDTO) throws NoSuchChatException, NoSuchUserException, MessageConflictException;

    Optional<MessageDTO> getMessageById(UUID messageId);

    List<MessageDTO> getAllMessages();

    MessageDTO modifyMessageById(UUID messageId, MessageEditableDTO messageEditableDTO) throws NoSuchMessageException, NoSuchChatException, NoSuchUserException, MessageConflictException;

    void removeMessageById(UUID messageId) throws NoSuchMessageException;
}
