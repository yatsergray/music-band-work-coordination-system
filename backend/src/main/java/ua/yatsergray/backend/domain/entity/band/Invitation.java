package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "token", unique = true, nullable = false)
    private UUID token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band", nullable = false)
    private Band band;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_participation_status", nullable = false)
    private ParticipationStatus participationStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invitation that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(token, that.token) && Objects.equals(band, that.band) && Objects.equals(participationStatus, that.participationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, token, band, participationStatus);
    }
}
