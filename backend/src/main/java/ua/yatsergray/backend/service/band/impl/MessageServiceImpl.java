package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.dto.band.editable.MessageEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.domain.entity.band.Message;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.exception.band.NoSuchMessageException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.MessageMapper;
import ua.yatsergray.backend.repository.band.ChatRepository;
import ua.yatsergray.backend.repository.band.MessageRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper, MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageDTO addMessage(MessageEditableDTO messageEditableDTO) throws NoSuchChatException, NoSuchUserException {
        Chat chat = chatRepository.findById(messageEditableDTO.getChatUUID())
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat does not exist with id=%s", messageEditableDTO.getChatUUID())));
        User user = userRepository.findById(messageEditableDTO.getUserUUID())
                .orElseThrow(() -> new NoSuchUserException(String.format("User does not exist with id=%s", messageEditableDTO.getUserUUID())));

        Message message = Message.builder()
                .text(messageEditableDTO.getText())
                .date(messageEditableDTO.getDate())
                .time(messageEditableDTO.getTime())
                .edited(messageEditableDTO.getEdited())
                .chat(chat)
                .user(user)
                .build();

        return messageMapper.mapToMessageDTO(messageRepository.save(message));
    }

    @Override
    public Optional<MessageDTO> getMessageById(UUID id) {
        return messageRepository.findById(id).map(messageMapper::mapToMessageDTO);
    }

    @Override
    public List<MessageDTO> getAllMessages() {
        return messageMapper.mapAllToMessageDTOList(messageRepository.findAll());
    }

    @Override
    public MessageDTO modifyMessageById(UUID id, MessageEditableDTO messageEditableDTO) throws NoSuchMessageException, NoSuchChatException, NoSuchUserException {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchMessageException(String.format("Message does not exist with id=%s", id)));
        Chat chat = chatRepository.findById(messageEditableDTO.getChatUUID())
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat does not exist with id=%s", messageEditableDTO.getChatUUID())));
        User user = userRepository.findById(messageEditableDTO.getUserUUID())
                .orElseThrow(() -> new NoSuchUserException(String.format("User does not exist with id=%s", messageEditableDTO.getUserUUID())));

        message.setText(messageEditableDTO.getText());
        message.setDate(messageEditableDTO.getDate());
        message.setTime(messageEditableDTO.getTime());
        message.setEdited(messageEditableDTO.getEdited());
        message.setChat(chat);
        message.setUser(user);

        return messageMapper.mapToMessageDTO(messageRepository.save(message));
    }

    @Override
    public void removeMessageById(UUID id) throws NoSuchMessageException {
        if (!messageRepository.existsById(id)) {
            throw new NoSuchMessageException(String.format("Message does not exist with id=%s", id));
        }

        messageRepository.deleteById(id);
    }
}
