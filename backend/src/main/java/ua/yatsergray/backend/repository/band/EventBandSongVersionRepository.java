package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.Event;
import ua.yatsergray.backend.domain.entity.band.EventBandSongVersion;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventBandSongVersionRepository extends JpaRepository<EventBandSongVersion, UUID> {

    boolean existsByEventIdAndSequenceNumber(UUID eventId, Integer eventBandSongVersionSequenceNumber);
}
