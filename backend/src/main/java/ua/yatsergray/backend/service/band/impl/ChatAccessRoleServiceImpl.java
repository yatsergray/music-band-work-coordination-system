package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.ChatAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;
import ua.yatsergray.backend.mapper.band.ChatAccessRoleMapper;
import ua.yatsergray.backend.repository.band.ChatAccessRoleRepository;
import ua.yatsergray.backend.repository.band.ChatUserAccessRoleRepository;
import ua.yatsergray.backend.service.band.ChatAccessRoleService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ChatAccessRoleServiceImpl implements ChatAccessRoleService {
    private final ChatAccessRoleMapper chatAccessRoleMapper;
    private final ChatAccessRoleRepository chatAccessRoleRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;

    @Autowired
    public ChatAccessRoleServiceImpl(ChatAccessRoleMapper chatAccessRoleMapper, ChatAccessRoleRepository chatAccessRoleRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository) {
        this.chatAccessRoleMapper = chatAccessRoleMapper;
        this.chatAccessRoleRepository = chatAccessRoleRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
    }

    @Override
    public ChatAccessRoleDTO addChatAccessRole(ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) throws ChatAccessRoleAlreadyExistsException {
        return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(configureChatAccessRole(new ChatAccessRole(), chatAccessRoleEditableDTO)));
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
    public ChatAccessRoleDTO modifyChatAccessRoleById(UUID chatAccessRoleId, ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) throws NoSuchChatAccessRoleException, ChatAccessRoleAlreadyExistsException {
        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findById(chatAccessRoleId)
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatAccessRoleId)));

        return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(configureChatAccessRole(chatAccessRole, chatAccessRoleEditableDTO)));
    }

    @Override
    public void removeChatAccessRoleById(UUID chatAccessRoleId) throws NoSuchChatAccessRoleException, ChildEntityExistsException {
        if (!chatAccessRoleRepository.existsById(chatAccessRoleId)) {
            throw new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatAccessRoleId));
        }

        checkIfChatAccessRoleHasChildEntity(chatAccessRoleId);

        chatAccessRoleRepository.deleteById(chatAccessRoleId);
    }

    private ChatAccessRole configureChatAccessRole(ChatAccessRole chatAccessRole, ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) throws ChatAccessRoleAlreadyExistsException {
        if (Objects.isNull(chatAccessRole.getId())) {
            if (chatAccessRoleRepository.existsByName(chatAccessRoleEditableDTO.getName())) {
                throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with name=\"%s\" already exists", chatAccessRoleEditableDTO.getName()));
            }

            if (chatAccessRoleRepository.existsByType(chatAccessRoleEditableDTO.getType())) {
                throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with type=\"%s\" already exists", chatAccessRoleEditableDTO.getType()));
            }
        } else {
            if (!chatAccessRoleEditableDTO.getName().equals(chatAccessRole.getName()) && chatAccessRoleRepository.existsByName(chatAccessRoleEditableDTO.getName())) {
                throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with name=\"%s\" already exists", chatAccessRoleEditableDTO.getName()));
            }

            if (!chatAccessRoleEditableDTO.getType().equals(chatAccessRole.getType()) && chatAccessRoleRepository.existsByType(chatAccessRoleEditableDTO.getType())) {
                throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with type=\"%s\" already exists", chatAccessRoleEditableDTO.getType()));
            }
        }

        chatAccessRole.setName(chatAccessRoleEditableDTO.getName());
        chatAccessRole.setType(chatAccessRoleEditableDTO.getType());

        return chatAccessRole;
    }

    private void checkIfChatAccessRoleHasChildEntity(UUID chatAccessRoleId) throws ChildEntityExistsException {
        long chatAccessRoleChildEntityAmount = chatUserAccessRoleRepository.countByChatAccessRoleId(chatAccessRoleId);

        if (chatAccessRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Chat user access role(s) depend(s) on the Chat access role with id=%s", chatAccessRoleChildEntityAmount, chatAccessRoleId));
        }
    }
}
