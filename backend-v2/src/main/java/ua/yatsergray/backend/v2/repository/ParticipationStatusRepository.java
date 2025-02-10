package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.ParticipationStatus;
import ua.yatsergray.backend.v2.domain.type.ParticipationStatusType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipationStatusRepository extends JpaRepository<ParticipationStatus, UUID> {

    Optional<ParticipationStatus> findByType(ParticipationStatusType participationStatusType);
}
