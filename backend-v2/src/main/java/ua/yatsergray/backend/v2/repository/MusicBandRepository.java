package ua.yatsergray.backend.v2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.MusicBand;

import java.util.UUID;

@Repository
public interface MusicBandRepository extends JpaRepository<MusicBand, UUID> {

    boolean existsByName(String name);

    @Query("""
            SELECT DISTINCT mb
            FROM MusicBand mb
            JOIN mb.musicBandUserAccessRoles mbar
            WHERE mbar.user.id = :userId
            """)
    Page<MusicBand> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);
}
