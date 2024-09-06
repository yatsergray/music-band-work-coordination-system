package ua.yatsergray.backend.domain.dto.band.editable;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatEditableDTO {
    private String imageFileName;
    private String name;
    private UUID bandUUID;
}
