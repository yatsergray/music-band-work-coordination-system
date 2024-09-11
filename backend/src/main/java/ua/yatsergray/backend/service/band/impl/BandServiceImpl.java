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
        Band band = Band.builder()
                .name(bandEditableDTO.getName())
                .build();

        return bandMapper.mapToBandDTO(bandRepository.save(band));
    }

    @Override
    public Optional<BandDTO> getBandById(UUID id) {
        return bandRepository.findById(id).map(bandMapper::mapToBandDTO);
    }

    @Override
    public List<BandDTO> getAllBands() {
        return bandMapper.mapAllToBandDTOList(bandRepository.findAll());
    }

    @Override
    public BandDTO modifyBandById(UUID id, BandEditableDTO bandEditableDTO) throws NoSuchBandException {
        return bandRepository.findById(id)
                .map(band -> {
                    band.setName(bandEditableDTO.getName());

                    return bandMapper.mapToBandDTO(bandRepository.save(band));
                })
                .orElseThrow(() -> new NoSuchBandException(String.format("Band does not exist with id=%s", id)));
    }

    @Override
    public void removeBandById(UUID id) throws NoSuchBandException {
        if (!bandRepository.existsById(id)) {
            throw new NoSuchBandException(String.format("Band does not exist with id=%s", id));
        }

        bandRepository.deleteById(id);
    }
}
