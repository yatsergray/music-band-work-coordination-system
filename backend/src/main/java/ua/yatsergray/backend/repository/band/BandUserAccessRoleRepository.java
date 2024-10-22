package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.BandUserAccessRole;

import java.util.List;
import java.util.UUID;

@Repository
public interface BandUserAccessRoleRepository extends JpaRepository<BandUserAccessRole, UUID> {

    boolean existsByBandAccessRoleId(UUID bandAccessRoleId);

    boolean existsByBandIdAndUserId(UUID bandId, UUID userId);

    void deleteByBandIdAndUserId(UUID bandId, UUID userId);

    boolean existsByBandIdAndUserIdAndBandAccessRoleId(UUID bandId, UUID userId, UUID bandAccessRoleId);

    void deleteByBandIdAndUserIdAndBandAccessRoleId(UUID bandId, UUID userId, UUID bandAccessRoleId);

    long countByBandAccessRoleId(UUID bandAccessRoleId);
}
