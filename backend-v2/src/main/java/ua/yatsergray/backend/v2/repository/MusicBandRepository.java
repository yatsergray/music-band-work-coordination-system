package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.MusicBand;

import java.util.UUID;

@Repository
public interface MusicBandRepository extends JpaRepository<MusicBand, UUID> {
}
