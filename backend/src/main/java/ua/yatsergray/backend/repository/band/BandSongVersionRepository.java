package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;

import java.util.UUID;

@Repository
public interface BandSongVersionRepository extends JpaRepository<BandSongVersion, UUID> {

    boolean existsByIdAndBandId(UUID bandSongVersionId, UUID BandSongVersionBandId);
}
