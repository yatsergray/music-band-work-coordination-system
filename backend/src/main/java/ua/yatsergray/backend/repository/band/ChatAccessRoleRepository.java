package ua.yatsergray.backend.repository.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.domain.entity.band.ChatAccessRole;
import ua.yatsergray.backend.domain.type.band.ChatAccessRoleType;

import java.util.UUID;

@Repository
public interface ChatAccessRoleRepository extends JpaRepository<ChatAccessRole, UUID> {

    boolean existsByName(String chatAccessRoleName);

    boolean existsByType(ChatAccessRoleType chatAccessRoleType);
}
