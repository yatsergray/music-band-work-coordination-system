package ua.yatsergray.backend.domain.entity.song;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.type.song.SongPartCategoryType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "song_part_categories")
public class SongPartCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "type", unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SongPartCategoryType type;

    @OneToMany(mappedBy = "songPartCategory")
    private Set<SongPart> songParts = new LinkedHashSet<>();
}
