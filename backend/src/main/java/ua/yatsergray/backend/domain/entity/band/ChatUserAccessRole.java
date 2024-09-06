package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.user.User;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "chat_user_access_roles")
public class ChatUserAccessRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat_access_role", nullable = false)
    private ChatAccessRole chatAccessRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatUserAccessRole that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(chat, that.chat) && Objects.equals(user, that.user) && Objects.equals(chatAccessRole, that.chatAccessRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chat, user, chatAccessRole);
    }
}