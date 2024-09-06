package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.Band;

import java.util.UUID;

@Repository
public interface BandRepository extends JpaRepository<Band, UUID> {
}
