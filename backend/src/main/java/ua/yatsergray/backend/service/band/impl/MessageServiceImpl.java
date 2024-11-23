package ua.yatsergray.backend.service.band.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.band.Message;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.domain.request.band.MessageCreateRequest;
import ua.yatsergray.backend.domain.request.band.MessageUpdateRequest;
import ua.yatsergray.backend.exception.band.MessageConflictException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.exception.band.NoSuchMessageException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.MessageMapper;
import ua.yatsergray.backend.repository.band.ChatRepository;
import ua.yatsergray.backend.repository.band.ChatUserAccessRoleRepository;
import ua.yatsergray.backend.repository.band.MessageRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.MessageService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatUserAccessRoleRepository chatUserAccessRoleRepository;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper, MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository, ChatUserAccessRoleRepository chatUserAccessRoleRepository) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.chatUserAccessRoleRepository = chatUserAccessRoleRepository;
    }

    @Override
    public MessageDTO addMessage(MessageCreateRequest messageCreateRequest) throws NoSuchChatException, NoSuchUserException, MessageConflictException {
        Chat chat = chatRepository.findById(messageCreateRequest.getChatId())
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=\"%s\" does not exist", messageCreateRequest.getChatId())));
        User user = userRepository.findById(messageCreateRequest.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=\"%s\" does not exist", messageCreateRequest.getUserId())));

        if (!chatUserAccessRoleRepository.existsByChatIdAndUserId(messageCreateRequest.getChatId(), messageCreateRequest.getUserId())) {
            throw new MessageConflictException(String.format("User with id=\"%s\" does not belong to the Chat with id=\"%s\"", messageCreateRequest.getUserId(), messageCreateRequest.getChatId()));
        }

        Message message = Message.builder()
                .text(messageCreateRequest.getText())
                .date(LocalDate.now())
                .time(LocalTime.now())
                .edited(false)
                .chat(chat)
                .user(user)
                .build();

        return messageMapper.mapToMessageDTO(messageRepository.save(message));
    }

    @Override
    public Optional<MessageDTO> getMessageById(UUID messageId) {
        return messageRepository.findById(messageId).map(messageMapper::mapToMessageDTO);
    }

    @Override
    public List<MessageDTO> getAllMessages() {
        return messageMapper.mapAllToMessageDTOList(messageRepository.findAll());
    }

    @Override
    public MessageDTO modifyMessageById(UUID messageId, MessageUpdateRequest messageUpdateRequest) throws NoSuchMessageException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchMessageException(String.format("Message with id=\"%s\" does not exist", messageId)));

        message.setText(messageUpdateRequest.getText());
        message.setEdited(!messageUpdateRequest.getText().equals(message.getText()));

        return messageMapper.mapToMessageDTO(messageRepository.save(message));
    }

    @Override
    public void removeMessageById(UUID messageId) throws NoSuchMessageException {
        if (!messageRepository.existsById(messageId)) {
            throw new NoSuchMessageException(String.format("Message with id=\"%s\" does not exist", messageId));
        }

        messageRepository.deleteById(messageId);
    }
}
