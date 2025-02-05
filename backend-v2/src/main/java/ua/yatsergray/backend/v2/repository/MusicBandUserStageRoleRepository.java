package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.MusicBandUserStageRole;

import java.util.UUID;

@Repository
public interface MusicBandUserStageRoleRepository extends JpaRepository<MusicBandUserStageRole, UUID> {

    boolean existsByMusicBandIdAndUserIdAndStageRoleId(UUID musicBandId, UUID userId, UUID stageRoleId);

    void deleteByMusicBandIdAndUserId(UUID musicBandId, UUID userId);

    void deleteByMusicBandIdAndUserIdAndStageRoleId(UUID musicBandId, UUID userId, UUID stageRoleId);

    long countByStageRoleId(UUID stageRoleId);
}
