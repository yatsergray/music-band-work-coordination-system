package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.song.SongInstrumentalPart;
import ua.yatsergray.backend.domain.type.band.StageRoleType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "stage_roles")
public class StageRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "type", unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StageRoleType type;

    @OneToMany(mappedBy = "stageRole")
    private Set<EventUser> eventUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "stageRole")
    private Set<BandUserStageRole> bandUserStageRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "stageRole")
    private Set<SongInstrumentalPart> songInstrumentalParts = new LinkedHashSet<>();
}
