package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.band.Chat;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;
import ua.yatsergray.backend.mapper.band.ChatMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.band.ChatRepository;
import ua.yatsergray.backend.service.band.ChatService;

import java.util.List;
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
    public ChatDTO addChat(ChatEditableDTO chatEditableDTO) throws NoSuchBandException {
        Band band = bandRepository.findById(chatEditableDTO.getBandUUID())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", chatEditableDTO.getBandUUID())));

        Chat chat = Chat.builder()
                .name(chatEditableDTO.getName())
                .band(band)
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
    public ChatDTO modifyChatById(UUID chatId, ChatEditableDTO chatEditableDTO) throws NoSuchChatException, NoSuchBandException {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new NoSuchChatException(String.format("Chat does not exist with id=%s", chatId)));
        Band band = bandRepository.findById(chatEditableDTO.getBandUUID())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", chatId)));

        chat.setName(chatEditableDTO.getName());
        chat.setBand(band);

        return chatMapper.mapToChatDTO(chatRepository.save(chat));
    }

    @Override
    public void removeChatById(UUID chatId) throws NoSuchChatException {
        if (!chatRepository.existsById(chatId)) {
            throw new NoSuchChatException(String.format("Chat does not exist with id=%s", chatId));
        }

        chatRepository.deleteById(chatId);
    }
}
