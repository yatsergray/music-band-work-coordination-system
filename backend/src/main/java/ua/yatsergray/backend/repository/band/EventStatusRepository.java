package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.EventStatus;
import ua.yatsergray.backend.domain.type.band.EventStatusType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventStatusRepository extends JpaRepository<EventStatus, UUID> {

    boolean existsByName(String eventStatusName);

    boolean existsByType(EventStatusType eventStatusType);

    Optional<EventStatus> findByType(EventStatusType eventStatusType);
}
