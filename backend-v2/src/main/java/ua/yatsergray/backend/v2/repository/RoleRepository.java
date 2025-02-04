package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.Role;
import ua.yatsergray.backend.v2.domain.type.RoleType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByName(String roleName);

    boolean existsByType(RoleType roleType);

    Optional<Role> findByType(RoleType roleType);
}
