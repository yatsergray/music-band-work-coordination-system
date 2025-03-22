package ua.yatsergray.backend.v2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query(value = """
            SELECT COUNT(e) > 0
            FROM events e
            WHERE e.music_band_id = :eventMusicBandId AND e.date = :eventDate AND (e.start_time < :eventEndTime AND e.end_time > :eventStartTime)
            """, nativeQuery = true)
    boolean existsOverlappingEvent(@Param("eventMusicBandId") UUID eventMusicBandId, @Param("eventDate") LocalDate eventDate, @Param("eventStartTime") LocalTime eventStartTime, @Param("eventEndTime") LocalTime eventEndTime);

    Page<Event> findAllByMusicBandId(UUID musicBandId, Pageable pageable);
}
