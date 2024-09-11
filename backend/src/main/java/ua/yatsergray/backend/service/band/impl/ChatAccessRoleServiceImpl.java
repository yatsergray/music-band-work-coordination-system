package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;
import ua.yatsergray.backend.mapper.band.ChatAccessRoleMapper;
import ua.yatsergray.backend.repository.band.ChatAccessRoleRepository;
import ua.yatsergray.backend.service.band.ChatAccessRoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ChatAccessRoleDTO addChatAccessRole(ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) {
        ChatAccessRole chatAccessRole = ChatAccessRole.builder()
                .name(chatAccessRoleEditableDTO.getName())
                .type(chatAccessRoleEditableDTO.getType())
                .build();

        return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(chatAccessRole));
    }

    @Override
    public Optional<ChatAccessRoleDTO> getChatAccessRoleById(UUID chatAccessRoleId) {
        return chatAccessRoleRepository.findById(chatAccessRoleId).map(chatAccessRoleMapper::mapToChatAccessRoleDTO);
    }

    @Override
    public List<ChatAccessRoleDTO> getAllChatAccessRoles() {
        return chatAccessRoleMapper.mapAllToChatAccessRoleDTOList(chatAccessRoleRepository.findAll());
    }

    @Override
    public ChatAccessRoleDTO modifyChatAccessRoleById(UUID chatAccessRoleId, ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) throws NoSuchChatAccessRoleException {
        return chatAccessRoleRepository.findById(chatAccessRoleId)
                .map(chatAccessRole -> {
                    chatAccessRole.setName(chatAccessRoleEditableDTO.getName());
                    chatAccessRole.setType(chatAccessRoleEditableDTO.getType());

                    return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(chatAccessRole));
                })
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role does not exist with id=%s", chatAccessRoleId)));
    }

    @Override
    public void removeChatAccessRoleById(UUID chatAccessRoleId) throws NoSuchChatAccessRoleException {
        if (!chatAccessRoleRepository.existsById(chatAccessRoleId)) {
            throw new NoSuchChatAccessRoleException(String.format("Chat access role does not exist with id=%s", chatAccessRoleId));
        }

        chatAccessRoleRepository.deleteById(chatAccessRoleId);
    }
}
