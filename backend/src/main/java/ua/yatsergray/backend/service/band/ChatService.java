package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.ChatUserDTO;
import ua.yatsergray.backend.domain.request.band.ChatCreateRequest;
import ua.yatsergray.backend.domain.request.band.ChatUpdateRequest;
import ua.yatsergray.backend.domain.request.band.ChatUserAccessRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.ChatUserCreateRequest;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.exception.user.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    ChatDTO addChat(ChatCreateRequest chatCreateRequest) throws NoSuchBandException, ChatAlreadyExistsException;

    Optional<ChatDTO> getChatById(UUID chatId);

    List<ChatDTO> getAllChats();

    ChatDTO modifyChatById(UUID chatId, ChatUpdateRequest chatUpdateRequest) throws NoSuchChatException, ChatAlreadyExistsException;

    void removeChatById(UUID chatId) throws NoSuchChatException;

    ChatUserDTO addChatUser(UUID chatId, ChatUserCreateRequest chatUserCreateRequest) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException, NoSuchChatAccessRoleException;

    void removeChatUser(UUID chatId, UUID userId) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException;

    ChatUserDTO addChatUserAccessRole(UUID chatId, UUID userId, ChatUserAccessRoleCreateRequest chatUserAccessRoleCreateRequest) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;

    void removeChatUserAccessRole(UUID chatId, UUID userId, UUID chatAccessRoleId) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;
}
