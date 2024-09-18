package ua.yatsergray.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.user.Role;
import ua.yatsergray.backend.domain.type.user.RoleType;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByName(String roleName);

    boolean existsByType(RoleType roleType);
}
