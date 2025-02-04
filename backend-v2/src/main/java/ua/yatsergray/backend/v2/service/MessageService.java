package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.MessageDTO;
import ua.yatsergray.backend.v2.domain.request.MessageCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MessageUpdateRequest;
import ua.yatsergray.backend.v2.exception.MessageConflictException;
import ua.yatsergray.backend.v2.exception.NoSuchChatException;
import ua.yatsergray.backend.v2.exception.NoSuchMessageException;
import ua.yatsergray.backend.v2.exception.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    MessageDTO addMessage(MessageCreateRequest messageCreateRequest) throws NoSuchChatException, NoSuchUserException, MessageConflictException;

    Optional<MessageDTO> getMessageById(UUID messageId);

    List<MessageDTO> getAllMessages();

    MessageDTO modifyMessageById(UUID messageId, MessageUpdateRequest messageUpdateRequest) throws NoSuchMessageException;

    void removeMessageById(UUID messageId) throws NoSuchMessageException;
}
