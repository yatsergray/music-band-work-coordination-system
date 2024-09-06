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
@Table(name = "event_users")
public class EventUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage_role", nullable = false)
    private StageRole stageRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_participation_status", nullable = false)
    private ParticipationStatus participationStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventUser eventUser)) return false;
        return Objects.equals(id, eventUser.id) && Objects.equals(user, eventUser.user) && Objects.equals(event, eventUser.event) && Objects.equals(stageRole, eventUser.stageRole) && Objects.equals(participationStatus, eventUser.participationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, event, stageRole, participationStatus);
    }
}
