package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.Invitation;

import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {

    boolean existsByMusicBandIdAndEmail(UUID invitationMusicBandId, String invitationEmail);

    long countByParticipationStatusId(UUID participationStatusId);
}
