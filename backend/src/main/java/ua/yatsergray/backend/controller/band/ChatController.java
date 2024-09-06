package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.ChatDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatEditableDTO;
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
    public ResponseEntity<ChatDTO> createChat(@RequestBody ChatEditableDTO chatEditableDTO) {
        return ResponseEntity.ok(chatService.addChat(chatEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> readChatById(@PathVariable("id") UUID id) {
        return chatService.getChatById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> readAllChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<ChatDTO> updateChatById(@PathVariable("id") UUID id, @RequestBody ChatEditableDTO chatEditableDTO) {
        return ResponseEntity.ok(chatService.modifyChatById(id, chatEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatById(@PathVariable("id") UUID id) {
        chatService.removeChatById(id);

        return ResponseEntity.ok().build();
    }
}
