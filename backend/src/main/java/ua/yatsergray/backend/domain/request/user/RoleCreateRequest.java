package ua.yatsergray.backend.domain.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ua.yatsergray.backend.domain.type.user.RoleType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleCreateRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Type is mandatory")
    private RoleType type;
}
