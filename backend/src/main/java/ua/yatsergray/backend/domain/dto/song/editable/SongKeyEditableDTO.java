package ua.yatsergray.backend.domain.dto.song.editable;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongKeyEditableDTO {

    @NotNull(message = "Key id is mandatory")
    private UUID keyId;
}
