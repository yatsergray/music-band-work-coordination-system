package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;
import ua.yatsergray.backend.domain.request.band.ChatAccessRoleCreateRequest;
import ua.yatsergray.backend.domain.request.band.ChatAccessRoleUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.ChatAccessRoleAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchChatAccessRoleException;
import ua.yatsergray.backend.mapper.band.ChatAccessRoleMapper;
import ua.yatsergray.backend.repository.band.ChatAccessRoleRepository;
import ua.yatsergray.backend.repository.band.ChatUserAccessRoleRepository;
import ua.yatsergray.backend.service.band.ChatAccessRoleService;

import java.util.List;
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
    public ChatAccessRoleDTO addChatAccessRole(ChatAccessRoleCreateRequest chatAccessRoleCreateRequest) throws ChatAccessRoleAlreadyExistsException {
        if (chatAccessRoleRepository.existsByName(chatAccessRoleCreateRequest.getName())) {
            throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with name=\"%s\" already exists", chatAccessRoleCreateRequest.getName()));
        }

        if (chatAccessRoleRepository.existsByType(chatAccessRoleCreateRequest.getType())) {
            throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with type=\"%s\" already exists", chatAccessRoleCreateRequest.getType()));
        }

        ChatAccessRole chatAccessRole = ChatAccessRole.builder()
                .name(chatAccessRoleCreateRequest.getName())
                .type(chatAccessRoleCreateRequest.getType())
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
    public ChatAccessRoleDTO modifyChatAccessRoleById(UUID chatAccessRoleId, ChatAccessRoleUpdateRequest chatAccessRoleUpdateRequest) throws NoSuchChatAccessRoleException, ChatAccessRoleAlreadyExistsException {
        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findById(chatAccessRoleId)
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatAccessRoleId)));

        if (!chatAccessRoleUpdateRequest.getName().equals(chatAccessRole.getName()) && chatAccessRoleRepository.existsByName(chatAccessRoleUpdateRequest.getName())) {
            throw new ChatAccessRoleAlreadyExistsException(String.format("Chat access role with name=\"%s\" already exists", chatAccessRoleUpdateRequest.getName()));
        }

        chatAccessRole.setName(chatAccessRoleUpdateRequest.getName());

        return chatAccessRoleMapper.mapToChatAccessRoleDTO(chatAccessRoleRepository.save(chatAccessRole));
    }

    @Override
    public void removeChatAccessRoleById(UUID chatAccessRoleId) throws NoSuchChatAccessRoleException, ChildEntityExistsException {
        if (!chatAccessRoleRepository.existsById(chatAccessRoleId)) {
            throw new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatAccessRoleId));
        }

        checkIfChatAccessRoleHasChildEntity(chatAccessRoleId);

        chatAccessRoleRepository.deleteById(chatAccessRoleId);
    }

    private void checkIfChatAccessRoleHasChildEntity(UUID chatAccessRoleId) throws ChildEntityExistsException {
        long chatAccessRoleChildEntityAmount = chatUserAccessRoleRepository.countByChatAccessRoleId(chatAccessRoleId);

        if (chatAccessRoleChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Chat user access role(s) depend(s) on the Chat access role with id=%s", chatAccessRoleChildEntityAmount, chatAccessRoleId));
        }
    }
}
