package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.EventCategory;
import ua.yatsergray.backend.domain.type.band.EventCategoryType;

import java.util.UUID;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, UUID> {

    boolean existsByName(String eventCategoryName);

    boolean existsByType(EventCategoryType eventCategoryType);
}
