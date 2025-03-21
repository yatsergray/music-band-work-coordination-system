package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.ChatDTO;
import ua.yatsergray.backend.v2.domain.dto.ChatUserDTO;
import ua.yatsergray.backend.v2.domain.request.ChatCreateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUpdateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUserAccessRoleCreateRequest;
import ua.yatsergray.backend.v2.domain.request.ChatUserCreateRequest;
import ua.yatsergray.backend.v2.service.ChatService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@Valid @RequestBody ChatCreateRequest chatCreateRequest) {
        return ResponseEntity.ok(chatService.addChat(chatCreateRequest));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> readChatById(@PathVariable("chatId") UUID chatId) {
        return chatService.getChatById(chatId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public ResponseEntity<List<ChatDTO>> readAllChats() {
//        return ResponseEntity.ok(chatService.getAllChats());
//    }

    @GetMapping
    public ResponseEntity<Page<ChatDTO>> readAllChatsByPageAndSize(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(chatService.getAllChatsByPageAndSize(page, size));
    }

    @SneakyThrows
    @PutMapping("/{chatId}")
    public ResponseEntity<ChatDTO> updateChatById(@PathVariable("chatId") UUID chatId, @Valid @RequestBody ChatUpdateRequest chatUpdateRequest) {
        return ResponseEntity.ok(chatService.modifyChatById(chatId, chatUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChatById(@PathVariable("chatId") UUID chatId) {
        chatService.removeChatById(chatId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{chatId}/users")
    public ResponseEntity<ChatUserDTO> createChatUser(@PathVariable("chatId") UUID chatId, @Valid @RequestBody ChatUserCreateRequest chatUserCreateRequest) {
        return ResponseEntity.ok(chatService.addChatUser(chatId, chatUserCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{chatId}/users/{userId}")
    public ResponseEntity<Void> deleteChatUser(@PathVariable("chatId") UUID chatId, @PathVariable("userId") UUID userId) {
        chatService.removeChatUser(chatId, userId);

        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PostMapping("/{chatId}/users/{userId}/chat-access-roles")
    public ResponseEntity<ChatUserDTO> createChatUserAccessRole(@PathVariable("chatId") UUID chatId, @PathVariable("userId") UUID userId, @Valid @RequestBody ChatUserAccessRoleCreateRequest chatUserAccessRoleCreateRequest) {
        return ResponseEntity.ok(chatService.addChatUserAccessRole(chatId, userId, chatUserAccessRoleCreateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{chatId}/users/{userId}/chat-access-roles/{chatAccessRoleId}")
    public ResponseEntity<Void> deleteChatUserAccessRole(@PathVariable("chatId") UUID chatId, @PathVariable("userId") UUID userId, @PathVariable("chatAccessRoleId") UUID chatAccessRoleId) {
        chatService.removeChatUserAccessRole(chatId, userId, chatAccessRoleId);

        return ResponseEntity.ok().build();
    }
}
