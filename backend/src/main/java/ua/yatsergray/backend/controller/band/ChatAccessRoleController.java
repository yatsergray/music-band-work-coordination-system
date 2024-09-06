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

    @PostMapping
    public ResponseEntity<ChatAccessRoleDTO> createChatAccessRole(@RequestBody ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) {
        return ResponseEntity.ok(chatAccessRoleService.addChatAccessRole(chatAccessRoleEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatAccessRoleDTO> readChatAccessRoleById(@PathVariable("id") UUID id) {
        return chatAccessRoleService.getChatAccessRoleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChatAccessRoleDTO>> readAllChatAccessRoles() {
        return ResponseEntity.ok(chatAccessRoleService.getAllChatAccessRoles());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<ChatAccessRoleDTO> updateChatAccessRoleById(@PathVariable("id") UUID id, @RequestBody ChatAccessRoleEditableDTO chatAccessRoleEditableDTO) {
        return ResponseEntity.ok(chatAccessRoleService.modifyChatAccessRoleById(id, chatAccessRoleEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatAccessRoleById(@PathVariable("id") UUID id) {
        chatAccessRoleService.removeChatAccessRoleById(id);

        return ResponseEntity.ok().build();
    }
}
