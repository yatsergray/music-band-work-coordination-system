package ua.yatsergray.backend.v2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.MusicBandUserAccessRole;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MusicBandUserAccessRoleRepository extends JpaRepository<MusicBandUserAccessRole, UUID> {

    Optional<MusicBandUserAccessRole> findByMusicBandIdAndUserId(UUID musicBandId, UUID userId);

    @Query("""
            SELECT mbr FROM MusicBandUserAccessRole mbr
            WHERE mbr.musicBand.id = :musicBandId
            AND NOT EXISTS (
                SELECT 1 FROM MusicBandUserAccessRole mbr2
                WHERE mbr2.musicBand.id = mbr.musicBand.id
                AND mbr2.user.id = mbr.user.id
                AND (mbr2.createdAt < mbr.createdAt OR (mbr2.createdAt = mbr.createdAt AND mbr2.id <> mbr.id))
            )
            """)
    Page<MusicBandUserAccessRole> findDistinctByMusicBandId(@Param("musicBandId") UUID musicBandId, Pageable pageable);


    void deleteByMusicBandIdAndUserId(UUID musicBandId, UUID userId);

    void deleteByMusicBandIdAndUserIdAndMusicBandAccessRoleId(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId);

    boolean existsByMusicBandIdAndUserId(UUID musicBandId, UUID userId);

    boolean existsByMusicBandIdAndUserIdAndMusicBandAccessRoleId(UUID musicBandId, UUID userId, UUID musicBandAccessRoleId);
}
