package ua.yatsergray.backend.service.band;

import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.exception.band.NoSuchBandException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BandService {

    BandDTO addBand(BandEditableDTO bandEditableDTO);

    Optional<BandDTO> getBandById(UUID id);

    List<BandDTO> getAllBands();

    BandDTO modifyBandById(UUID id, BandEditableDTO bandEditableDTO) throws NoSuchBandException;

    void removeBandById(UUID id) throws NoSuchBandException;
}
