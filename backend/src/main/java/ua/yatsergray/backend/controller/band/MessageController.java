package ua.yatsergray.backend.controller.band;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.MessageDTO;
import ua.yatsergray.backend.domain.dto.band.editable.MessageEditableDTO;
import ua.yatsergray.backend.service.band.impl.MessageServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageServiceImpl messageService;

    @Autowired
    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@Valid @RequestBody MessageEditableDTO messageEditableDTO) {
        return ResponseEntity.ok(messageService.addMessage(messageEditableDTO));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDTO> readMessageById(@PathVariable("messageId") UUID messageId) {
        return messageService.getMessageById(messageId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> readMessageAlls() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @SneakyThrows
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageDTO> updateMessageById(@PathVariable("messageId") UUID messageId, @Valid @RequestBody MessageEditableDTO messageEditableDTO) {
        return ResponseEntity.ok(messageService.modifyMessageById(messageId, messageEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable("messageId") UUID messageId) {
        messageService.removeMessageById(messageId);

        return ResponseEntity.ok().build();
    }
}
