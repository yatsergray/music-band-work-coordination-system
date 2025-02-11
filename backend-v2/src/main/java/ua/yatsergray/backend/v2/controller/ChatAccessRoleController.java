package ua.yatsergray.backend.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yatsergray.backend.v2.domain.dto.ChatAccessRoleDTO;
import ua.yatsergray.backend.v2.service.ChatAccessRoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat-access-roles")
public class ChatAccessRoleController {
    private final ChatAccessRoleService chatAccessRoleService;

    @Autowired
    public ChatAccessRoleController(ChatAccessRoleService chatAccessRoleService) {
        this.chatAccessRoleService = chatAccessRoleService;
    }

    @GetMapping("/{chatAccessRoleId}")
    public ResponseEntity<ChatAccessRoleDTO> readChatAccessRoleById(@PathVariable("chatAccessRoleId") UUID chatAccessRoleId) {
        return chatAccessRoleService.getChatAccessRoleById(chatAccessRoleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChatAccessRoleDTO>> readAllChatAccessRoles() {
        return ResponseEntity.ok(chatAccessRoleService.getAllChatAccessRoles());
    }
}
