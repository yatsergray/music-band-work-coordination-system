package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.StageRole;
import ua.yatsergray.backend.domain.type.band.StageRoleType;

import java.util.UUID;

@Repository
public interface StageRoleRepository extends JpaRepository<StageRole, UUID> {

    boolean existsByName(String stageRoleName);

    boolean existsByType(StageRoleType stageRoleType);
}
