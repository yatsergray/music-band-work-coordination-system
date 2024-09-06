package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.entity.band.BandSongVersion;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "song_structures")
public class SongStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "songStructure")
    private Song song;

    @OneToMany(mappedBy = "songStructure")
    private Set<BandSongVersion> bandSongVersions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "songStructure")
    private Set<SongPartStructureDetails> songPartStructureDetailsSet = new LinkedHashSet<>();
}
