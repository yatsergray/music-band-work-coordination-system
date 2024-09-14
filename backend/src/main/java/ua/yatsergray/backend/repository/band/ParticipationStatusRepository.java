package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.ParticipationStatus;
import ua.yatsergray.backend.domain.type.band.ParticipationStatusType;

import java.util.UUID;

@Repository
public interface ParticipationStatusRepository extends JpaRepository<ParticipationStatus, UUID> {

    boolean existsByName(String participationStatusName);

    boolean existsByType(ParticipationStatusType participationStatusType);
}
