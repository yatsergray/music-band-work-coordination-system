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
@Table(name = "bands")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_file_id", unique = true)
    private UUID imageFileId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "band")
    private Set<Chat> chats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band")
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band")
    private Set<Invitation> invitations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band")
    private Set<BandSongVersion> bandSongVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band")
    private Set<BandUserStageRole> bandUserStageRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "band")
    private Set<BandUserAccessRole> bandUserAccessRoles = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "bands")
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Band band)) return false;
        return Objects.equals(id, band.id) && Objects.equals(imageFileId, band.imageFileId) && Objects.equals(name, band.name) && Objects.equals(chats, band.chats) && Objects.equals(events, band.events) && Objects.equals(invitations, band.invitations) && Objects.equals(bandSongVersions, band.bandSongVersions) && Objects.equals(bandUserStageRoles, band.bandUserStageRoles) && Objects.equals(bandUserAccessRoles, band.bandUserAccessRoles) && Objects.equals(users, band.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageFileId, name, chats, events, invitations, bandSongVersions, bandUserStageRoles, bandUserAccessRoles, users);
    }
}
