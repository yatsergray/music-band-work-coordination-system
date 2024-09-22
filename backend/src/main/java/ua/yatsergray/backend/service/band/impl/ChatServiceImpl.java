package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.band.ChatAlreadyExistsException;
import ua.yatsergray.backend.exception.band.ChatUserConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.ChatMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.BandUserAccessRoleRepository;
import ua.yatsergray.backend.repository.band.ChatRepository;
import ua.yatsergray.backend.repository.band.ChatUserAccessRoleRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.ChatService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    private final BandRepository bandRepository;
    private final UserRepository userRepository;
    private final BandUserAccessRoleRepository bandUserAccessRoleRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;

    @Autowired
    public ChatServiceImpl(ChatMapper chatMapper, ChatRepository chatRepository, BandRepository bandRepository,
                           UserRepository userRepository, BandUserAccessRoleRepository bandUserAccessRoleRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository) {
        this.chatMapper = chatMapper;
        this.chatRepository = chatRepository;
        this.bandRepository = bandRepository;
        this.userRepository = userRepository;
        this.bandUserAccessRoleRepository = bandUserAccessRoleRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
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
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=%s does not exist", chatId)));

        return chatMapper.mapToChatDTO(chatRepository.save(configureChat(chat, chatEditableDTO)));
    }

    @Override
    public void removeChatById(UUID chatId) throws NoSuchChatException {
        if (!chatRepository.existsById(chatId)) {
            throw new NoSuchChatException(String.format("Chat with id=%s does not exist", chatId));
        }

        chatRepository.deleteById(chatId);
    }

    @Override
    public ChatDTO addChatUser(ChatUserEditableDTO chatUserEditableDTO) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException {
        Chat chat = chatRepository.findById(chatUserEditableDTO.getChatId())
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=%s does not exist", chatUserEditableDTO.getChatId())));
        User user = userRepository.findById(chatUserEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", chatUserEditableDTO.getUserId())));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(chat.getBand().getId(), chatUserEditableDTO.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=%s does not belong to the Band with id%s", chatUserEditableDTO.getUserId(), chat.getBand().getId()));
        }

        if (chatUserAccessRoleRepository.existsByChatIdAndUserId(chatUserEditableDTO.getChatId(), chatUserEditableDTO.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=%s already belongs to the Chat with id=%s", chatUserEditableDTO.getUserId(), chatUserEditableDTO.getChatId()));
        }

        chat.getUsers().add(user);

        return chatMapper.mapToChatDTO(chatRepository.save(chat));
    }

    @Override
    public ChatDTO removeChatUser(ChatUserEditableDTO chatUserEditableDTO) throws NoSuchChatException, NoSuchUserException, ChatUserConflictException {
        Chat chat = chatRepository.findById(chatUserEditableDTO.getChatId())
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=%s does not exist", chatUserEditableDTO.getChatId())));
        User user = userRepository.findById(chatUserEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", chatUserEditableDTO.getUserId())));

        if (!bandUserAccessRoleRepository.existsByBandIdAndUserId(chat.getBand().getId(), chatUserEditableDTO.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=%s does not belong to the Band with id%s", chatUserEditableDTO.getUserId(), chat.getBand().getId()));
        }

        if (!chatUserAccessRoleRepository.existsByChatIdAndUserId(chatUserEditableDTO.getChatId(), chatUserEditableDTO.getUserId())) {
            throw new ChatUserConflictException(String.format("User with id=%s does not belong to the Chat with id=%s", chatUserEditableDTO.getUserId(), chatUserEditableDTO.getChatId()));
        }

        chat.getUsers().add(user);

        return chatMapper.mapToChatDTO(chatRepository.save(chat));
    }

    private Chat configureChat(Chat chat, ChatEditableDTO chatEditableDTO) throws NoSuchBandException, ChatAlreadyExistsException {
        Band band = bandRepository.findById(chatEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=%s does not exist", chatEditableDTO.getBandId())));

        if (Objects.isNull(chat.getId())) {
            if (chatRepository.existsByBandIdAndName(chatEditableDTO.getBandId(), chatEditableDTO.getName())) {
                throw new ChatAlreadyExistsException(String.format("Chat with bandId=%s and name=%s already exists", chatEditableDTO.getBandId(), chatEditableDTO.getName()));
            }
        } else {
            if ((!chatEditableDTO.getBandId().equals(chat.getBand().getId()) || !chatEditableDTO.getName().equals(chat.getName())) && chatRepository.existsByBandIdAndName(chatEditableDTO.getBandId(), chatEditableDTO.getName())) {
                throw new ChatAlreadyExistsException(String.format("Chat with bandId=%s and name=%s already exists", chatEditableDTO.getBandId(), chatEditableDTO.getName()));
            }
        }

        chat.setName(chatEditableDTO.getName());
        chat.setBand(band);

        return chat;
    }
}
