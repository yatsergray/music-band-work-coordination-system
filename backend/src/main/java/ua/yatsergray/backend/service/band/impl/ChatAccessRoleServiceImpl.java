package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatAccessRoleEditableDTO;
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
        return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(chatAccessRoleMapper.mapToChatAccessRole(chatAccessRoleEditableDTO)));
    }

    @Override
    public Optional<ChatAccessRoleDTO> getChatAccessRoleById(UUID id) {
        return chatAccessRoleRepository.findById(id).map(chatAccessRoleMapper::mapToChatAccessRoleDTO);
    }

    @Override
    public List<ChatAccessRoleDTO> getAllChatAccessRoles() {
        return chatAccessRoleMapper.mapAllToChatAccessRoleDTOList(chatAccessRoleRepository.findAll());
    }

    @Override
    public ChatAccessRoleDTO modifyChatAccessRoleById(UUID id, ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) throws NoSuchChatAccessRoleException {
        return chatAccessRoleRepository.findById(id)
                .map(chatAccessRole -> {
                    chatAccessRole.setName(chatAccessRoleEditableDTO.getName());
                    chatAccessRole.setType(chatAccessRoleEditableDTO.getType());

                    return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(chatAccessRole));
                })
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role does not exist with id=%s", id)));
    }

    @Override
    public void removeChatAccessRoleById(UUID id) throws NoSuchChatAccessRoleException {
        if (!chatAccessRoleRepository.existsById(id)) {
            throw new NoSuchChatAccessRoleException(String.format("Chat access role does not exist with id=%s", id));
        }

        chatAccessRoleRepository.deleteById(id);
    }
}
