package ua.yatsergray.backend.v2.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.v2.domain.dto.InvitationDTO;
import ua.yatsergray.backend.v2.domain.request.InvitationCreateRequest;
import ua.yatsergray.backend.v2.domain.request.InvitationUpdateRequest;
import ua.yatsergray.backend.v2.service.impl.InvitationServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invitations")
public class InvitationController {
    private final InvitationServiceImpl invitationService;

    @Autowired
    public InvitationController(InvitationServiceImpl invitationService) {
        this.invitationService = invitationService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@Valid @RequestBody InvitationCreateRequest invitationCreateRequest) {
        return ResponseEntity.ok(invitationService.addInvitation(invitationCreateRequest));
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<InvitationDTO> readInvitationById(@PathVariable("invitationId") UUID invitationId) {
        return invitationService.getInvitationById(invitationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> readAllInvitations() {
        return ResponseEntity.ok(invitationService.getAllInvitations());
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
