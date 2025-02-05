package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query(value = """
            SELECT COUNT(e) > 0
            FROM events e
            WHERE e.id_band = :eventBandId AND e.date = :eventDate AND (e.start_time < :eventEndTime AND e.end_time > :eventStartTime)
            """, nativeQuery = true)
    boolean existsOverlappingEvent(@Param("eventBandId") UUID eventBandId, @Param("eventDate") LocalDate eventDate, @Param("eventStartTime") LocalTime eventStartTime, @Param("eventEndTime") LocalTime eventEndTime);

    long countByEventCategoryId(UUID eventCategoryId);

    long countByRoomId(UUID roomId);

    long countByEventStatusId(UUID eventStatusId);
}
