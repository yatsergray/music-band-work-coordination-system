package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.ChatUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserEditableDTO;
import ua.yatsergray.backend.exception.band.*;
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

    ChatUserDTO addChatUser(UUID chatId, ChatUserEditableDTO chatUserEditableDTO) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException, NoSuchChatAccessRoleException;

    void removeChatUser(UUID chatId, UUID userId) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException;

    ChatUserDTO addChatUserAccessRole(UUID chatId, UUID userId, ChatUserAccessRoleEditableDTO chatUserAccessRoleEditableDTO) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;

    void removeChatUserAccessRole(UUID chatId, UUID userId, UUID chatAccessRoleId) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;
}
