package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.type.band.ParticipationStatusType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "participation_statuses")
public class ParticipationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "type", unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ParticipationStatusType type;

    @OneToMany(mappedBy = "participationStatus")
    @Builder.Default
    private Set<EventUser> eventUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "participationStatus")
    @Builder.Default
    private Set<Invitation> invitations = new LinkedHashSet<>();
}
