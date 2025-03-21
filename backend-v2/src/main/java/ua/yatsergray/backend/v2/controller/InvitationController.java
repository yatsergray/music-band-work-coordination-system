package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.InvitationDTO;
import ua.yatsergray.backend.v2.domain.request.InvitationCreateRequest;
import ua.yatsergray.backend.v2.domain.request.InvitationUpdateRequest;
import ua.yatsergray.backend.v2.provider.RabbitMQPropertyProvider;
import ua.yatsergray.backend.v2.service.InvitationService;
import ua.yatsergray.backend.v2.service.RabbitMQProducerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invitations")
public class InvitationController {
    private final InvitationService invitationService;
    private final RabbitMQPropertyProvider rabbitMQPropertyProvider;
    private final RabbitMQProducerService<InvitationDTO> emailInvitationRabbitMQProviderService;

    @Autowired
    public InvitationController(InvitationService invitationService, RabbitMQPropertyProvider rabbitMQPropertyProvider, RabbitMQProducerService<InvitationDTO> emailInvitationRabbitMQProviderService) {
        this.invitationService = invitationService;
        this.rabbitMQPropertyProvider = rabbitMQPropertyProvider;
        this.emailInvitationRabbitMQProviderService = emailInvitationRabbitMQProviderService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@Valid @RequestBody InvitationCreateRequest invitationCreateRequest) {
        InvitationDTO invitationDTO = invitationService.addInvitation(invitationCreateRequest);

        emailInvitationRabbitMQProviderService.sendMessage(rabbitMQPropertyProvider.getEmailInvitationRoutingKeyName(), invitationDTO);

        return ResponseEntity.ok(invitationDTO);
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<InvitationDTO> readInvitationById(@PathVariable("invitationId") UUID invitationId) {
        return invitationService.getInvitationById(invitationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public ResponseEntity<List<InvitationDTO>> readAllInvitations() {
//        return ResponseEntity.ok(invitationService.getAllInvitations());
//    }

    @GetMapping
    public ResponseEntity<Page<InvitationDTO>> readAllInvitationsByPageAndSize(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(invitationService.getAllInvitationsByPageAndSize(page, size));
    }

    @SneakyThrows
    @PutMapping("/{invitationId}")
    public ResponseEntity<InvitationDTO> updateInvitationById(@PathVariable("invitationId") UUID invitationId, @Valid @RequestBody InvitationUpdateRequest invitationUpdateRequest) {
        return ResponseEntity.ok(invitationService.modifyInvitationById(invitationId, invitationUpdateRequest));
    }

    @SneakyThrows
    @DeleteMapping("/{invitationId}")
    public ResponseEntity<Void> deleteInvitationById(@PathVariable("invitationId") UUID invitationId) {
        invitationService.removeInvitationById(invitationId);

        return ResponseEntity.ok().build();
    }
}
