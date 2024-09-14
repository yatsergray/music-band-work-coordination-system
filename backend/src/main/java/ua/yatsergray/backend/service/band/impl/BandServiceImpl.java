package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.mapper.band.BandMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.service.band.BandService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BandServiceImpl implements BandService {
    private final BandMapper bandMapper;
    private final BandRepository bandRepository;

    @Autowired
    public BandServiceImpl(BandMapper bandMapper, BandRepository bandRepository) {
        this.bandMapper = bandMapper;
        this.bandRepository = bandRepository;
    }

    @Override
    public BandDTO addBand(BandEditableDTO bandEditableDTO) {
        return bandMapper.mapToBandDTO(bandRepository.save(configureBand(new Band(), bandEditableDTO)));
    }

    @Override
    public Optional<BandDTO> getBandById(UUID bandId) {
        return bandRepository.findById(bandId).map(bandMapper::mapToBandDTO);
    }

    @Override
    public List<BandDTO> getAllBands() {
        return bandMapper.mapAllToBandDTOList(bandRepository.findAll());
    }

    @Override
    public BandDTO modifyBandById(UUID bandId, BandEditableDTO bandEditableDTO) throws NoSuchBandException {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=%s does not exist", bandId)));

        return bandMapper.mapToBandDTO(bandRepository.save(configureBand(band, bandEditableDTO)));

    }

    @Override
    public void removeBandById(UUID bandId) throws NoSuchBandException {
        if (!bandRepository.existsById(bandId)) {
            throw new NoSuchBandException(String.format("Band with id=%s does not exist", bandId));
        }

        bandRepository.deleteById(bandId);
    }

    private Band configureBand(Band band, BandEditableDTO bandEditableDTO) {
        band.setName(bandEditableDTO.getName());

        return band;
    }
}
