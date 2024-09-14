package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.BandAccessRole;
import ua.yatsergray.backend.domain.type.band.BandAccessRoleType;

import java.util.UUID;

@Repository
public interface BandAccessRoleRepository extends JpaRepository<BandAccessRole, UUID> {

    boolean existsByName(String bandAccessRoleName);

    boolean existsByType(BandAccessRoleType bandAccessRoleType);
}
