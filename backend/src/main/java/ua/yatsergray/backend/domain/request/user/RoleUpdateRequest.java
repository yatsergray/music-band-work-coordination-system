package ua.yatsergray.backend.domain.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleUpdateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
