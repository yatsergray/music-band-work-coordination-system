package ua.yatsergray.backend.v2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.EventSong;

import java.util.UUID;

@Repository
public interface EventSongRepository extends JpaRepository<EventSong, UUID> {

    boolean existsByEventIdAndSequenceNumber(UUID eventId, Integer eventSongSequenceNumber);

    Page<EventSong> findAllByEventId(UUID eventId, Pageable pageable);
}
