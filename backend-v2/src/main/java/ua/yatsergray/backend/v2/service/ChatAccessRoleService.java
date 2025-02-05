package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.ChatAccessRoleDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatAccessRoleService {

    Optional<ChatAccessRoleDTO> getChatAccessRoleById(UUID chatAccessRoleId);

    List<ChatAccessRoleDTO> getAllChatAccessRoles();
}
