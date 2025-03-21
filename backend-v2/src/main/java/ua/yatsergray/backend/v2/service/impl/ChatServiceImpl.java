package ua.yatsergray.backend.v2.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.domain.dto.ChatDTO;
import ua.yatsergray.backend.v2.domain.dto.ChatUserDTO;
import ua.yatsergray.backend.v2.domain.entity.*;
import ua.yatsergray.backend.v2.domain.request.ChatCreateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUpdateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUserCreateRequest;
import ua.yatsergray.backend.v2.domain.type.ChatAccessRoleType;
import ua.yatsergray.backend.v2.exception.*;
import ua.yatsergray.backend.v2.mapper.ChatMapper;
import ua.yatsergray.backend.v2.mapper.ChatUserMapper;
import ua.yatsergray.backend.v2.repository.*;
import ua.yatsergray.backend.v2.service.ChatService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ChatServiceImpl implements ChatService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    private final MusicBandRepository musicBandRepository;
    private final UserRepository userRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;
    private final ChatAccessRoleRepository chatAccessRoleRepository;
    private final MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository;

    @Autowired
    public ChatServiceImpl(ChatMapper chatMapper, ChatRepository chatRepository, MusicBandRepository musicBandRepository, UserRepository userRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository, ChatAccessRoleRepository chatAccessRoleRepository, MusicBandUserAccessRoleRepository musicBandUserAccessRoleRepository) {
        this.chatMapper = chatMapper;
        this.chatRepository = chatRepository;
        this.musicBandRepository = musicBandRepository;
        this.userRepository = userRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
        this.chatAccessRoleRepository = chatAccessRoleRepository;
        this.musicBandUserAccessRoleRepository = musicBandUserAccessRoleRepository;
    }

    @Override
    public ChatDTO addChat(ChatCreateRequest chatCreateRequest) throws NoSuchMusicBandException, ChatAlreadyExistsException {
        MusicBand musicBand = musicBandRepository.findById(chatCreateRequest.getMusicBandId())
                .orElseThrow(() -> new NoSuchMusicBandException(String.format("Music band with id=\"%s\" does not exist", chatCreateRequest.getMusicBandId())));

        if (chatRepository.existsByMusicBandIdAndName(chatCreateRequest.getMusicBandId(), chatCreateRequest.getName())) {
            throw new ChatAlreadyExistsException(String.format("Chat with bandId=\"%s\" and name=\"%s\" already exists", chatCreateRequest.getMusicBandId(), chatCreateRequest.getName()));
        }

        Chat chat = Chat.builder()
                .name(chatCreateRequest.getName())
                .createdAt(LocalDateTime.now())
                .musicBand(musicBand)
                .build();

        return chatMapper.mapToChatDTO(chatRepository.save(chat));
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
    public ChatDTO modifyChatById(UUID chatId, ChatUpdateRequest chatUpdateRequest) throws NoSuchChatException, ChatAlreadyExistsException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));

        if (!chatUpdateRequest.getName().equals(chat.getName()) && chatRepository.existsByMusicBandIdAndName(chat.getMusicBand().getId(), chatUpdateRequest.getName())) {
            throw new ChatAlreadyExistsException(String.format("Chat with bandId=\"%s\" and name=\"%s\" already exists", chat.getMusicBand().getId(), chatUpdateRequest.getName()));
        }

        chat.setName(chatUpdateRequest.getName());

        return chatMapper.mapToChatDTO(chatRepository.save(chat));
    }

    @Override
    public void removeChatById(UUID chatId) throws NoSuchChatException {
        if (!chatRepository.existsById(chatId)) {
            throw new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId));
        }

        chatRepository.deleteById(chatId);
    }

    @Override
    public ChatUserDTO addChatUser(UUID chatId, ChatUserCreateRequest chatUserCreateRequest) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));
        User user = userRepository.findById(chatUserCreateRequest.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", chatUserCreateRequest.getUserId())));
        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findByType(ChatAccessRoleType.MEMBER)
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with type=\"%s\" does not exist", ChatAccessRoleType.MEMBER)));

        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(chat.getMusicBand().getId(), chatUserCreateRequest.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\"", chatUserCreateRequest.getUserId(), chat.getMusicBand().getId()));
        }

        if (chatUserAccessRoleRepository.existsByChatIdAndUserId(chatId, chatUserCreateRequest.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" already belongs to the Chat with id=\"%s\"", chatUserCreateRequest.getUserId(), chatId));
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
    public ChatUserDTO addChatUserAccessRole(UUID chatId, UUID userId, ChatUserAccessRoleCreateRequest chatUserAccessRoleCreateRequest) throws NoSuchChatException, NoSuchUserException, NoSuchChatAccessRoleException, ChatUserConflictException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", chatId)));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", userId)));
        ChatAccessRole chatAccessRole = chatAccessRoleRepository.findById(chatUserAccessRoleCreateRequest.getChatAccessRoleId())
                .orElseThrow(() -> new NoSuchChatAccessRoleException(String.format("Chat access role with id=\"%s\" does not exist", chatUserAccessRoleCreateRequest.getChatAccessRoleId())));

        checkUserBelongingToBandAndChat(chatId, userId, chat);

        if (chatUserAccessRoleRepository.existsByChatIdAndUserIdAndChatAccessRoleId(chatId, userId, chatUserAccessRoleCreateRequest.getChatAccessRoleId())) {
            throw new ChatUserConflictException(String.format("Chat user access role with chatId=\"%s\", userId=\"%s\" and accessRoleId=\"%s\" already exists", chatId, userId, chatUserAccessRoleCreateRequest.getChatAccessRoleId()));
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

    private void checkUserBelongingToBandAndChat(UUID chatId, UUID userId, Chat chat) throws ChatUserConflictException {
        if (!musicBandUserAccessRoleRepository.existsByMusicBandIdAndUserId(chat.getMusicBand().getId(), userId)) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Music band with id=\"%s\" of the Chat with id=\"%s\"", userId, chat.getMusicBand().getId(), chatId));
        }

        if (!chatUserAccessRoleRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new ChatUserConflictException(String.format("User with id=\"%s\" does not belong to the Chat with id=\"%s\"", userId, chatId));
        }
    }
}
