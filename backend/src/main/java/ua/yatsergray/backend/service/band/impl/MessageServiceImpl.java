package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.dto.band.editable.MessageEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.band.Message;
import ua.yatsergray.backend.domain.entity.user.User;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
    public MessageDTO addMessage(MessageEditableDTO messageEditableDTO) throws NoSuchChatException, NoSuchUserException, MessageConflictException {
        return messageMapper.mapToMessageDTO(messageRepository.save(configureMessage(new Message(), messageEditableDTO)));
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
    public MessageDTO modifyMessageById(UUID messageId, MessageEditableDTO messageEditableDTO) throws NoSuchMessageException, NoSuchChatException, NoSuchUserException, MessageConflictException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchMessageException(String.format("Message with id=%s does not exist", messageId)));

        return messageMapper.mapToMessageDTO(messageRepository.save(configureMessage(message, messageEditableDTO)));
    }

    @Override
    public void removeMessageById(UUID messageId) throws NoSuchMessageException {
        if (!messageRepository.existsById(messageId)) {
            throw new NoSuchMessageException(String.format("Message with id=%s does not exist", messageId));
        }

        messageRepository.deleteById(messageId);
    }

    private Message configureMessage(Message message, MessageEditableDTO messageEditableDTO) throws NoSuchChatException, NoSuchUserException, MessageConflictException {
        Chat chat = chatRepository.findById(messageEditableDTO.getChatId())
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat with id=%s does not exist", messageEditableDTO.getChatId())));
        User user = userRepository.findById(messageEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", messageEditableDTO.getUserId())));

        if (chatUserAccessRoleRepository.existsByChatIdAndUserId(messageEditableDTO.getChatId(), messageEditableDTO.getUserId())) {
            throw new MessageConflictException(String.format("User with id=%s is not a member of Chat with id=%s", messageEditableDTO.getUserId(), messageEditableDTO.getChatId()));
        }

        if (!Objects.isNull(message.getId())) {
            message.setEdited(!messageEditableDTO.getText().equals(message.getText()));
        }

        message.setText(messageEditableDTO.getText());
        message.setDate(messageEditableDTO.getDate());
        message.setTime(messageEditableDTO.getTime());
        message.setChat(chat);
        message.setUser(user);

        return message;
    }
}
