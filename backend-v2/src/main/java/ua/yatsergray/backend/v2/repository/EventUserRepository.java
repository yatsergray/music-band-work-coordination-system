package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.EventUser;

import java.util.UUID;

@Repository
public interface EventUserRepository extends JpaRepository<EventUser, UUID> {

    boolean existsByEventIdAndUserIdAndStageRoleId(UUID eventId, UUID userId, UUID stageRoleId);
}
