package ua.yatsergray.backend.controller.band;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.dto.band.editable.InvitationEditableDTO;
import ua.yatsergray.backend.service.band.impl.InvitationServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invitations")
public class InvitationController {
    private final InvitationServiceImpl invitationService;

    @Autowired
    public InvitationController(InvitationServiceImpl invitationService) {
        this.invitationService = invitationService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@RequestBody InvitationEditableDTO invitationEditableDTO) {
        return ResponseEntity.ok(invitationService.addInvitation(invitationEditableDTO));
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
    public ResponseEntity<InvitationDTO> updateInvitationById(@PathVariable("invitationId") UUID invitationId, @RequestBody InvitationEditableDTO invitationEditableDTO) {
        return ResponseEntity.ok(invitationService.modifyInvitationById(invitationId, invitationEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{invitationId}")
    public ResponseEntity<Void> deleteInvitationById(@PathVariable("invitationId") UUID invitationId) {
        invitationService.removeInvitationById(invitationId);

        return ResponseEntity.ok().build();
    }
}
