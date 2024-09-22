package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserEditableDTO;
import ua.yatsergray.backend.exception.band.ChatAlreadyExistsException;
import ua.yatsergray.backend.exception.band.ChatUserConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    ChatDTO addChat(ChatEditableDTO chatEditableDTO) throws NoSuchBandException, ChatAlreadyExistsException;

    Optional<ChatDTO> getChatById(UUID chatId);

    List<ChatDTO> getAllChats();

    ChatDTO modifyChatById(UUID chatId, ChatEditableDTO chatEditableDTO) throws NoSuchChatException, NoSuchBandException, ChatAlreadyExistsException;

    void removeChatById(UUID chatId) throws NoSuchChatException;

    ChatDTO addChatUser(ChatUserEditableDTO chatUserEditableDTO) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException;

    ChatDTO removeChatUser(ChatUserEditableDTO chatUserEditableDTO) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException;
}
