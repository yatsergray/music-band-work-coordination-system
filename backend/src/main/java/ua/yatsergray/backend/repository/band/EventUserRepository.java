package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.EventUser;

import java.util.UUID;

@Repository
public interface EventUserRepository extends JpaRepository<EventUser, UUID> {
}
