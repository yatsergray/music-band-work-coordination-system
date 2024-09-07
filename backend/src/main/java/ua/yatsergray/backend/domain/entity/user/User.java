package ua.yatsergray.backend.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.band.*;

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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_file_id", unique = true, nullable = false)
    private UUID imageFileId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Message> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventUser> eventUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<BandUserStageRole> bandUserStageRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<BandUserAccessRole> bandUserAccessRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ChatUserAccessRole> chatUserAccessRoles = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "band_users",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_band")}
    )
    private Set<Band> bands = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "chat_users",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_chat")}
    )
    private Set<Chat> chats = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(imageFileId, user.imageFileId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(messages, user.messages) && Objects.equals(eventUsers, user.eventUsers) && Objects.equals(bandUserStageRoles, user.bandUserStageRoles) && Objects.equals(bandUserAccessRoles, user.bandUserAccessRoles) && Objects.equals(roles, user.roles) && Objects.equals(bands, user.bands) && Objects.equals(chats, user.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageFileId, firstName, lastName, email, password, messages, eventUsers, bandUserStageRoles, bandUserAccessRoles, roles, bands, chats);
    }
}
