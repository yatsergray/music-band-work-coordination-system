package ua.yatsergray.backend.domain.request.band;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ua.yatsergray.backend.domain.type.band.ParticipationStatusType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParticipationStatusCreateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Type is mandatory")
    private ParticipationStatusType type;
}