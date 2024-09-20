package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.exception.band.ChatAlreadyExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.mapper.band.ChatMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.ChatRepository;
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

    @Autowired
    public ChatServiceImpl(ChatMapper chatMapper, ChatRepository chatRepository, BandRepository bandRepository) {
        this.chatMapper = chatMapper;
        this.chatRepository = chatRepository;
        this.bandRepository = bandRepository;
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
