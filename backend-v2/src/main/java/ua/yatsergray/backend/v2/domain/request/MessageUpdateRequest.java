package ua.yatsergray.backend.v2.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageUpdateRequest {

    @NotBlank(message = "Text is mandatory")
    private String text;
}
