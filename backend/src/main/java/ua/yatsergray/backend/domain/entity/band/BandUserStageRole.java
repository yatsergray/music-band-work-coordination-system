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
@Table(name = "band_user_stage_roles")
public class BandUserStageRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_band", nullable = false)
    private Band band;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stage_role", nullable = false)
    private StageRole stageRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandUserStageRole that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(band, that.band) && Objects.equals(user, that.user) && Objects.equals(stageRole, that.stageRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, band, user, stageRole);
    }
}
