package ua.yatsergray.backend.controller.band;

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
@RequestMapping("/messages")
public class MessageController {
    private final MessageServiceImpl messageService;

    @Autowired
    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageEditableDTO messageEditableDTO) {
        return ResponseEntity.ok(messageService.addMessage(messageEditableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> readMessageById(@PathVariable("id") UUID id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> readMessageAlls() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateMessageById(@PathVariable("id") UUID id, @RequestBody MessageEditableDTO messageEditableDTO) {
        return ResponseEntity.ok(messageService.modifyMessageById(id, messageEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable("id") UUID id) {
        messageService.removeMessageById(id);

        return ResponseEntity.ok().build();
    }
}
