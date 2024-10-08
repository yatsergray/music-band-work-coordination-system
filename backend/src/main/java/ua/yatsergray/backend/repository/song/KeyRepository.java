package ua.yatsergray.backend.repository.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.song.Key;

import java.util.UUID;

@Repository
public interface KeyRepository extends JpaRepository<Key, UUID> {

    boolean existsByName(String keyName);
}
