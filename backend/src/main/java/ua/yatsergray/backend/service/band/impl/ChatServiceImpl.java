package ua.yatsergray.backend.service.band.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.ChatUserDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserAccessRoleEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;
import ua.yatsergray.backend.domain.entity.band.ChatUserAccessRole;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.domain.type.band.ChatAccessRoleType;
import ua.yatsergray.backend.exception.band.*;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.ChatMapper;
import ua.yatsergray.backend.mapper.band.ChatUserMapper;
import ua.yatsergray.backend.repository.band.*;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.ChatService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ChatServiceImpl implements ChatService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    private final BandRepository bandRepository;
    private final UserRepository userRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;
    private final ChatAccessRoleRepository chatAccessRoleRepository;
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;

    @Autowired
    public ChatServiceImpl(ChatMapper chatMapper, ChatRepository chatRepository, BandRepository bandRepository, UserRepository userRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository, ChatAccessRoleRepository chatAccessRoleRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository) {
        this.chatMapper = chatMapper;
        this.chatRepository = chatRepository;
        this.bandRepository = bandRepository;
        this.userRepository = userRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
        this.chatAccessRoleRepository = chatAccessRoleRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
    }

    @Override
    public ChatDTO addChat(ChatEditableDTO chatEditableDTO) throws NoSuchBandException, ChatAlreadyExistsException {
        return chatMapper.mapToChatDTO(chatRepository.save(configureChat(new Chat(), chatEditableDTO)));
    }

    @Override
    public Optional<ChatDTO> getChatById(UUID chatId) {
        return chatRepository.findById(chatId).map(chatMapper::mapToChatDTO);
    }

    @Override
    public List<ChatDTO> getAllChats() {
        return chatMapper.mapAllToChatDTOList(chatRepository.findAll());
    }

    @Override
    public ChatDTO modifyChatById(UUID chatId, ChatEditableDTO chatEditableDTO) throws NoSuchChatException, NoSuchBandException, ChatAlreadyExistsException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));

        return chatMapper.mapToChatDTO(chatRepository.save(configureChat(chat, chatEditableDTO)));
    }

    @Override
    public void removeChatById(UUID chatId) throws NoSuchChatException {
        if (!chatRepository.existsById(chatId)) {
            throw new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId));
        }

        chatRepository.deleteById(chatId);
    }

    @Override
    public ChatUserDTO addChatUser(UUID chatId, ChatUserEditableDTO chatUserEditableDTO) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException, NoSuchChatAccessRoleException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));
        User user = userRepository.findById(chatUserEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", chatUserEditableDTO.getUserId())));
        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findByType(ChatAccessRoleType.MEMBER)
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with type=\"%s\" does not exist", ChatAccessRoleType.MEMBER)));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(chat.getBand().getId(), chatUserEditableDTO.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\"", chatUserEditableDTO.getUserId(), chat.getBand().getId()));
        }

        if (chatUserAccessRoleRepository.existsByChatIdAndUserId(chatId, chatUserEditableDTO.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" already belongs to the Chat with id=\"%s\"", chatUserEditableDTO.getUserId(), chatId));
        }

        ChatUserAccessRole chatUserAccessRole = ChatUserAccessRole.builder()
                .chat(chat)
                .user(user)
                .chatAccessRole(chatAccessRole)
                .build();

        chatUserAccessRoleRepository.save(chatUserAccessRole);

        entityManager.refresh(chat);
        entityManager.refresh(user);

        return ChatUserMapper.INSTANCE.mapToChatUserDTO(chat, user);
    }

    @Override
    public void removeChatUser(UUID chatId, UUID userId) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException {
        if (!chatRepository.existsById(chatId)) {
            throw new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId));
        }

        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        if (!chatUserAccessRoleRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Chat with id=\"%s\"", userId, chatId));
        }

        chatUserAccessRoleRepository.deleteByChatIdAndUserId(chatId, userId);
    }

    @Override
    public ChatUserDTO addChatUserAccessRole(UUID chatId, UUID userId, ChatUserAccessRoleEditableDTO chatUserAccessRoleEditableDTO) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findById(chatUserAccessRoleEditableDTO.getChatAccessRoleId())
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatUserAccessRoleEditableDTO.getChatAccessRoleId())));

        checkUserBelongingToBandAndChat(chatId, userId, chat);

        if (chatUserAccessRoleRepository.existsByChatIdAndUserIdAndChatAccessRoleId(chatId, userId, chatUserAccessRoleEditableDTO.getChatAccessRoleId())) {
            throw new ChatUserConflictException(String.format("Chat user access role with chatId=\"%s\", userId=\"%s\" and accessRoleId=\"%s\" already exists", chatId, userId, chatUserAccessRoleEditableDTO.getChatAccessRoleId()));
        }

        ChatUserAccessRole chatUserAccessRole = ChatUserAccessRole.builder()
                .chat(chat)
                .user(user)
                .chatAccessRole(chatAccessRole)
                .build();

        chatUserAccessRoleRepository.save(chatUserAccessRole);

        entityManager.refresh(chat);
        entityManager.refresh(user);

        return ChatUserMapper.INSTANCE.mapToChatUserDTO(chat, user);
    }

    @Override
    public void removeChatUserAccessRole(UUID chatId, UUID userId, UUID chatAccessRoleId) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));

        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId));
        }

        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findById(chatAccessRoleId)
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatAccessRoleId)));

        checkUserBelongingToBandAndChat(chatId, userId, chat);

        if (!chatUserAccessRoleRepository.existsByChatIdAndUserIdAndChatAccessRoleId(chatId, userId, chatAccessRoleId)) {
            throw new ChatUserConflictException(String.format("Chat user access role with chatId=\"%s\", userId=\"%s\" and chatAccessRoleId=\"%s\" does not exist", chatId, userId, chatAccessRoleId));
        }

        if (chatAccessRole.getType().equals(ChatAccessRoleType.MEMBER)) {
            throw new ChatUserConflictException(String.format("Chat access role with id=\"%s\" cannot be removed from User with id=\"%s\" in the Chat with id=\"%s\"", chatAccessRoleId, userId, chatId));
        }

        chatUserAccessRoleRepository.deleteByChatIdAndUserIdAndChatAccessRoleId(chatId, userId, chatAccessRoleId);
    }

    private Chat configureChat(Chat chat, ChatEditableDTO chatEditableDTO) throws NoSuchBandException, ChatAlreadyExistsException {
        Band band = bandRepository.findById(chatEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", chatEditableDTO.getBandId())));

        if (Objects.isNull(chat.getId())) {
            if (chatRepository.existsByBandIdAndName(chatEditableDTO.getBandId(), chatEditableDTO.getName())) {
                throw new ChatAlreadyExistsException(String.format("Chat with bandId=\"%s\" and name=\"%s\" already exists", chatEditableDTO.getBandId(), chatEditableDTO.getName()));
            }
        } else {
            if ((!chatEditableDTO.getBandId().equals(chat.getBand().getId()) || !chatEditableDTO.getName().equals(chat.getName())) && chatRepository.existsByBandIdAndName(chatEditableDTO.getBandId(), chatEditableDTO.getName())) {
                throw new ChatAlreadyExistsException(String.format("Chat with bandId=\"%s\" and name=\"%s\" already exists", chatEditableDTO.getBandId(), chatEditableDTO.getName()));
            }
        }

        chat.setName(chatEditableDTO.getName());
        chat.setBand(band);

        return chat;
    }

    private void checkUserBelongingToBandAndChat(UUID chatId, UUID userId, Chat chat) throws ChatUserConflictException {
        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(chat.getBand().getId(), userId)) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Band with id=\"%s\" of the Chat with id=\"%s\"", userId, chat.getBand().getId(), chatId));
        }

        if (!chatUserAccessRoleRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Chat with id=\"%s\"", userId, chatId));
        }
    }
}
