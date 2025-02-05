package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.MusicBandAccessRole;
import ua.yatsergray.backend.v2.domain.type.MusicBandAccessRoleType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MusicBandAccessRoleRepository extends JpaRepository<MusicBandAccessRole, UUID> {

    boolean existsByName(String musicBandAccessRoleName);

    boolean existsByType(MusicBandAccessRoleType musicBandAccessRoleType);

    Optional<MusicBandAccessRole> findByType(MusicBandAccessRoleType musicBandAccessRoleType);
}
