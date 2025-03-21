package ua.yatsergray.backend.v2.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvitationDTO {
    private UUID id;
    private String email;
    private String token;
    private UUID musicBandId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("participationStatus")
    private ParticipationStatusDTO participationStatusDTO;
}
