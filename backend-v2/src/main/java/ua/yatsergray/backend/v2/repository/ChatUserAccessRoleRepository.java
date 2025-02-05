package ua.yatsergray.backend.v2.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.yatsergray.backend.v2.domain.entity.ChatUserAccessRole;

import java.util.UUID;

@Repository
public interface ChatUserAccessRoleRepository extends JpaRepository<ChatUserAccessRole, UUID> {

    boolean existsByChatIdAndUserIdAndChatAccessRoleId(UUID chatId, UUID userId, UUID chatAccessRoleId);

    void deleteByChatIdAndUserIdAndChatAccessRoleId(UUID chatId, UUID userId, UUID chatAccessRoleId);

    boolean existsByChatIdAndUserId(UUID chatId, UUID userId);

    void deleteByChatIdAndUserId(UUID chatId, UUID userId);

    long countByChatAccessRoleId(UUID chatAccessRoleId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE
            FROM chat_user_access_roles cuar
            WHERE cuar.user_id = :userId AND cuar.chat_id IN (
                SELECT c.id
                FROM chats c
                WHERE c.music_band_id = :musicBandId
            )
            """, nativeQuery = true)
    void deleteByMusicBandIdAndUserId(@Param("musicBandId") UUID musicBandId, @Param("userId") UUID userId);
}
