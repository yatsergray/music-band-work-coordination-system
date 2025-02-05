package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatUpdateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
