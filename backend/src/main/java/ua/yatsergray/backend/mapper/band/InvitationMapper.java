package ua.yatsergray.backend.mapper.band;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.domain.dto.band.InvitationDTO;
import ua.yatsergray.backend.domain.entity.band.Invitation;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParticipationStatusMapper.class})
public interface InvitationMapper {

    InvitationMapper INSTANCE = Mappers.getMapper(InvitationMapper.class);

    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    InvitationDTO mapToInvitationDTO(Invitation invitation);

    List<InvitationDTO> mapAllToInvitationDTOList(List<Invitation> invitations);
}
