package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.MusicBandUserAccessRole;

import java.util.UUID;

@Repository
public interface MusicBandUserAccessRoleRepository extends JpaRepository<MusicBandUserAccessRole, UUID> {

    void deleteByMusicBandIdAndUserId(UUID musicBandId, UUID userId);

    void deleteByMusicBandIdAndUserIdAndMusicBandAccessRoleId(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId);

    boolean existsByMusicBandIdAndUserId(UUID musicBandId, UUID userId);

    boolean existsByMusicBandIdAndUserIdAndMusicBandAccessRoleId(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId);
}
