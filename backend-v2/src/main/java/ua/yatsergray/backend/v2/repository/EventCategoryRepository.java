package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.EventCategory;
import ua.yatsergray.backend.v2.domain.type.EventCategoryType;

import java.util.UUID;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, UUID> {

    boolean existsByName(String eventCategoryName);

    boolean existsByType(EventCategoryType eventCategoryType);
}
