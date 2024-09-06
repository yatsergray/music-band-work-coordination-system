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

    @GetMapping("/{id}")
    public ResponseEntity<InvitationDTO> readInvitationById(@PathVariable("id") UUID id) {
        return invitationService.getInvitationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> readAllInvitations() {
        return ResponseEntity.ok(invitationService.getAllInvitations());
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<InvitationDTO> updateInvitationById(@PathVariable("id") UUID id, @RequestBody InvitationEditableDTO invitationEditableDTO) {
        return ResponseEntity.ok(invitationService.modifyInvitationById(id, invitationEditableDTO));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitationById(@PathVariable("id") UUID id) {
        invitationService.removeInvitationById(id);

        return ResponseEntity.ok().build();
    }
}
