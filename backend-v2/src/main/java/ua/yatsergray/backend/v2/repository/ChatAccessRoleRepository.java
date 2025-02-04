package ua.yatsergray.backend.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.ChatAccessRole;
import ua.yatsergray.backend.v2.domain.type.ChatAccessRoleType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatAccessRoleRepository extends JpaRepository<ChatAccessRole, UUID> {

    boolean existsByName(String chatAccessRoleName);

    boolean existsByType(ChatAccessRoleType chatAccessRoleType);

    Optional<ChatAccessRole> findByType(ChatAccessRoleType chatAccessRoleType);
}
