package ua.yatsergray.backend.domain.entity.band;

import jakarta.persistence.*;
import lombok.*;
import ua.yatsergray.backend.domain.type.band.ChatAccessRoleType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "chat_access_roles")
public class ChatAccessRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "type", unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ChatAccessRoleType type;

    @OneToMany(mappedBy = "chatAccessRole")
    @Builder.Default
    private Set<ChatUserAccessRole> chatUserAccessRoles = new LinkedHashSet<>();
}
