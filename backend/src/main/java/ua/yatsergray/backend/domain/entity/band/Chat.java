package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.user.User;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_file_id", unique = true)
    private UUID imageFileId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band", nullable = false)
    private Band band;

    @OneToMany(mappedBy = "chat")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "chat")
    private Set<ChatUserAccessRole> chatUserAccessRoles = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "chats")
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat chat)) return false;
        return Objects.equals(id, chat.id) && Objects.equals(imageFileId, chat.imageFileId) && Objects.equals(name, chat.name) && Objects.equals(band, chat.band) && Objects.equals(messages, chat.messages) && Objects.equals(chatUserAccessRoles, chat.chatUserAccessRoles) && Objects.equals(users, chat.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageFileId, name, band, messages, chatUserAccessRoles, users);
    }
}
