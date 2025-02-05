package ua.yatsergray.backend.v2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.ChatAccessRoleDTO;
import ua.yatsergray.backend.v2.mapper.ChatAccessRoleMapper;
import ua.yatsergray.backend.v2.repository.ChatAccessRoleRepository;
import ua.yatsergray.backend.v2.service.ChatAccessRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ChatAccessRoleServiceImpl implements ChatAccessRoleService {
    private final ChatAccessRoleMapper chatAccessRoleMapper;
    private final ChatAccessRoleRepository chatAccessRoleRepository;

    @Autowired
    public ChatAccessRoleServiceImpl(ChatAccessRoleMapper chatAccessRoleMapper, ChatAccessRoleRepository chatAccessRoleRepository) {
        this.chatAccessRoleMapper = chatAccessRoleMapper;
        this.chatAccessRoleRepository = chatAccessRoleRepository;
    }

    @Override
    public Optional<ChatAccessRoleDTO> getChatAccessRoleById(UUID chatAccessRoleId) {
        return chatAccessRoleRepository.findById(chatAccessRoleId).map(chatAccessRoleMapper::mapToChatAccessRoleDTO);
    }

    @Override
    public List<ChatAccessRoleDTO> getAllChatAccessRoles() {
        return chatAccessRoleMapper.mapAllToChatAccessRoleDTOList(chatAccessRoleRepository.findAll());
    }
}
