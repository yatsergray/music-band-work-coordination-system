package ua.yatsergray.backend.domain.request.song;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SongPartKeyChordUpdateRequest {

    @NotNull(message = "Chord id is mandatory")
    private UUID chordId;
}
