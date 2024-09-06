package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.band.NoSuchChatException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {

    ChatDTO addChat(ChatEditableDTO chatEditableDTO) throws NoSuchBandException;

    Optional<ChatDTO> getChatById(UUID id);

    List<ChatDTO> getAllChats();

    ChatDTO modifyChatById(UUID id, ChatEditableDTO chatEditableDTO) throws NoSuchChatException, NoSuchBandException;

    void removeChatById(UUID id) throws NoSuchChatException;
}
