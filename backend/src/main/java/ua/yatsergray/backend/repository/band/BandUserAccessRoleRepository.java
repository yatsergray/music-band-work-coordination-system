package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.BandUserAccessRole;

import java.util.UUID;

@Repository
public interface BandUserAccessRoleRepository extends JpaRepository<BandUserAccessRole, UUID> {

    boolean existsByBandIdAndUserId(UUID bandId, UUID userId);

    void deleteByBandIdAndUserId(UUID bandId, UUID userId);

    boolean existsByBandIdAndUserIdAndBandAccessRoleId(UUID bandId, UUID userId, UUID bandAccessRoleId);

    void deleteByBandIdAndUserIdAndBandAccessRoleId(UUID bandId, UUID userId, UUID bandAccessRoleId);

    long countByBandAccessRoleId(UUID bandAccessRoleId);
}
