package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.request.band.ChatAccessRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.ChatAccessRoleUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.ChatAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatAccessRoleService {

    ChatAccessRoleDTO addChatAccessRole(ChatAccessRoleCreateRequest chatAccessRoleCreateRequest) throws ChatAccessRoleAlreadyExistsException;

    Optional<ChatAccessRoleDTO> getChatAccessRoleById(UUID chatAccessRoleId);

    List<ChatAccessRoleDTO> getAllChatAccessRoles();

    ChatAccessRoleDTO modifyChatAccessRoleById(UUID chatAccessRoleId, ChatAccessRoleUpdateRequest chatAccessRoleUpdateRequest) throws NoSuchChatAccessRoleException, ChatAccessRoleAlreadyExistsException;

    void removeChatAccessRoleById(UUID chatAccessRoleId) throws NoSuchChatAccessRoleException, ChildEntityExistsException;
}
