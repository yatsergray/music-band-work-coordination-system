package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.ChatAccessRoleDTO;
import ua.yatsergray.backend.domain.dto.band.editable.ChatAccessRoleEditableDTO;
import ua.yatsergray.backend.service.band.impl.ChatAccessRoleServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat-access-roles")
public class ChatAccessRoleController {
    private final ChatAccessRoleServiceImpl chatAccessRoleService;

    @Autowired
    public ChatAccessRoleController(ChatAccessRoleServiceImpl chatAccessRoleService) {
        this.chatAccessRoleService = chatAccessRoleService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ChatAccessRoleDTO> createChatAccessRole(@RequestBody ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) {
        return ResponseEntity.ok(chatAccessRoleService.addChatAccessRole(chatAccessRoleEditableDTO));
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

    @SneakyThrows
    @PutMapping("/{chatAccessRoleId}")
    public ResponseEntity<ChatAccessRoleDTO> updateChatAccessRoleById(@PathVariable("chatAccessRoleId") UUID chatAccessRoleId, @RequestBody ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) {
        return ResponseEntity.ok(chatAccessRoleService.modifyChatAccessRoleById(chatAccessRoleId, chatAccessRoleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{chatAccessRoleId}")
    public ResponseEntity<Void> deleteChatAccessRoleById(@PathVariable("chatAccessRoleId") UUID chatAccessRoleId) {
        chatAccessRoleService.removeChatAccessRoleById(chatAccessRoleId);

        return ResponseEntity.ok().build();
    }
}
