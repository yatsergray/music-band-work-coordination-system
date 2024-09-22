package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatUserEditableDTO;
import ua.yatsergray.backend.service.band.impl.ChatServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatServiceImpl chatService;

    @Autowired
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@Valid @RequestBody ChatEditableDTO chatEditableDTO) {
        return ResponseEntity.ok(chatService.addChat(chatEditableDTO));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> readChatById(@PathVariable("chatId") UUID chatId) {
        return chatService.getChatById(chatId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> readAllChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @SneakyThrows
    @PutMapping("/{chatId}")
    public ResponseEntity<ChatDTO> updateChatById(@PathVariable("chatId") UUID chatId, @Valid @RequestBody ChatEditableDTO chatEditableDTO) {
        return ResponseEntity.ok(chatService.modifyChatById(chatId, chatEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChatById(@PathVariable("chatId") UUID chatId) {
        chatService.removeChatById(chatId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/create-chat-user")
    public ResponseEntity<ChatDTO> createChatUser(@Valid @RequestBody ChatUserEditableDTO chatUserEditableDTO) {
        return ResponseEntity.ok(chatService.addChatUser(chatUserEditableDTO));
    }

    @SneakyThrows
    @PostMapping("/delete-chat-user")
    public ResponseEntity<ChatDTO> deleteChatUser(@Valid @RequestBody ChatUserEditableDTO chatUserEditableDTO) {
        return ResponseEntity.ok(chatService.removeChatUser(chatUserEditableDTO));
    }
}
