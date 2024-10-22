package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.BandUserStageRole;

import java.util.UUID;

@Repository
public interface BandUserStageRoleRepository extends JpaRepository<BandUserStageRole, UUID> {

    boolean existsByBandIdAndUserIdAndStageRoleId(UUID bandId, UUID userId, UUID stageRoleId);

    void deleteByBandIdAndUserId(UUID bandId, UUID userId);

    void deleteByBandIdAndUserIdAndStageRoleId(UUID bandId, UUID userId, UUID stageRoleId);

    long countByStageRoleId(UUID stageRoleId);
}
