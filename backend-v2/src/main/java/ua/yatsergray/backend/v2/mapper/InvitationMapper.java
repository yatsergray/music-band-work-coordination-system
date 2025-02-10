package ua.yatsergray.backend.v2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.yatsergray.backend.v2.domain.dto.InvitationDTO;
import ua.yatsergray.backend.v2.domain.entity.Invitation;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParticipationStatusMapper.class})
public interface InvitationMapper {

    InvitationMapper INSTANCE = Mappers.getMapper(InvitationMapper.class);

    @Mapping(source = "musicBand.id", target = "musicBandId")
    @Mapping(source = "participationStatus", target = "participationStatusDTO")
    InvitationDTO mapToInvitationDTO(Invitation invitation);

    List<InvitationDTO> mapAllToInvitationDTOList(List<Invitation> invitations);
}
