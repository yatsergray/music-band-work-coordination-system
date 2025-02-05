package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.MessageDTO;
import ua.yatsergray.backend.v2.domain.request.MessageCreateRequest;
import ua.yatsergray.backend.v2.domain.request.MessageUpdateRequest;
import ua.yatsergray.backend.v2.service.impl.MessageServiceImpl;

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
    public ResponseEntity<MessageDTO> createMessage(@Valid @RequestBody MessageCreateRequest messageCreateRequest) {
        return ResponseEntity.ok(messageService.addMessage(messageCreateRequest));
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
    public ResponseEntity<MessageDTO> updateMessageById(@PathVariable("messageId") UUID messageId, @Valid @RequestBody MessageUpdateRequest messageUpdateRequest) {
        return ResponseEntity.ok(messageService.modifyMessageById(messageId, messageUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable("messageId") UUID messageId) {
        messageService.removeMessageById(messageId);

        return ResponseEntity.ok().build();
    }
}
