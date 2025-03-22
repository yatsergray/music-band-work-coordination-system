package ua.yatsergray.backend.v2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String userEmail);

    boolean existsByEmail(String userEmail);

    @Query("""
            SELECT DISTINCT u
            FROM User u
            JOIN u.musicBandUserAccessRoles mbar
            WHERE mbar.musicBand.id = :musicBandId
            """)
    Page<User> findAllByMusicBandId(@Param("musicBandId") UUID musicBandId, Pageable pageable);
}
