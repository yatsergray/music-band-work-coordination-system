package ua.yatsergray.backend.v2.service;

import org.springframework.data.domain.Page;
import ua.yatsergray.backend.v2.domain.dto.ChatDTO;
import ua.yatsergray.backend.v2.domain.dto.ChatUserDTO;
import ua.yatsergray.backend.v2.domain.request.ChatCreateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUpdateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUserCreateRequest;
import ua.yatsergray.backend.v2.exception.*;

import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    ChatDTO addChat(ChatCreateRequest chatCreateRequest) throws NoSuchMusicBandException, ChatAlreadyExistsException;

    Optional<ChatDTO> getChatById(UUID chatId);

//    List<ChatDTO> getAllChats();

//    Page<ChatDTO> getAllChatsByPageAndSize(int page, int size);

    Page<ChatDTO> getAllChatsByMusicBandIdAndPageAndSize(UUID musicBandId, int page, int size);

    ChatDTO modifyChatById(UUID chatId, ChatUpdateRequest chatUpdateRequest) throws NoSuchChatException, ChatAlreadyExistsException;

    void removeChatById(UUID chatId) throws NoSuchChatException;

    ChatUserDTO addChatUser(UUID chatId, ChatUserCreateRequest chatUserCreateRequest) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;

    void removeChatUser(UUID chatId, UUID userId) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException;

    ChatUserDTO addChatUserAccessRole(UUID chatId, UUID userId, ChatUserAccessRoleCreateRequest chatUserAccessRoleCreateRequest) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;

    void removeChatUserAccessRole(UUID chatId, UUID userId, UUID chatAccessRoleId) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException;
}
