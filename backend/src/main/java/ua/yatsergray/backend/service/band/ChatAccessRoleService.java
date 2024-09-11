package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatAccessRoleEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatAccessRoleService {

    ChatAccessRoleDTO addChatAccessRole(ChatAccessRoleEditableDTO chatAccessRoleEditableDTO);

    Optional<ChatAccessRoleDTO> getChatAccessRoleById(UUID chatAccessRoleId);

    List<ChatAccessRoleDTO> getAllChatAccessRoles();

    ChatAccessRoleDTO modifyChatAccessRoleById(UUID chatAccessRoleId, ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) throws NoSuchChatAccessRoleException;

    void removeChatAccessRoleById(UUID chatAccessRoleId) throws NoSuchChatAccessRoleException;
}
