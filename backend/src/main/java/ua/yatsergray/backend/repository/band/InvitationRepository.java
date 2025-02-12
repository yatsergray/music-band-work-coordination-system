package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.Invitation;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {

    boolean existsByBandIdAndEmail(UUID invitationBandId, String invitationEmail);

    long countByParticipationStatusId(UUID participationStatusId);
}
