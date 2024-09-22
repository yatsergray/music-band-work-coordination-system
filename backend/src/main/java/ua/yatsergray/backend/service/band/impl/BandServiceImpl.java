package ua.yatsergray.backend.service.band.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.band.BandDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandEditableDTO;
import ua.yatsergray.backend.domain.dto.band.editable.BandUserEditableDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.user.User;
import ua.yatsergray.backend.exception.band.BandUserConflictException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.user.NoSuchUserException;
import ua.yatsergray.backend.mapper.band.BandMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.user.UserRepository;
import ua.yatsergray.backend.service.band.BandService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BandServiceImpl implements BandService {
    private final BandMapper bandMapper;
    private final BandRepository bandRepository;
    private final UserRepository userRepository;

    @Autowired
    public BandServiceImpl(BandMapper bandMapper, BandRepository bandRepository,
                           UserRepository userRepository) {
        this.bandMapper = bandMapper;
        this.bandRepository = bandRepository;
        this.userRepository = userRepository;
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

    @Override
    public BandDTO addBandUser(BandUserEditableDTO bandUserEditableDTO) throws NoSuchBandException, NoSuchUserException, BandUserConflictException {
        Band band = bandRepository.findById(bandUserEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=%s does not exist", bandUserEditableDTO.getBandId())));
        User user = userRepository.findById(bandUserEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", bandUserEditableDTO.getUserId())));

        if (band.getUsers().contains(user)) {
            throw new BandUserConflictException(String.format("User with id=%s already belongs to the Band with id=%s", bandUserEditableDTO.getUserId(), bandUserEditableDTO.getBandId()));
        }

        band.getUsers().add(user);

        return bandMapper.mapToBandDTO(bandRepository.save(band));
    }

    @Override
    public BandDTO removeBandUser(BandUserEditableDTO bandUserEditableDTO) throws NoSuchBandException, NoSuchUserException, BandUserConflictException {
        Band band = bandRepository.findById(bandUserEditableDTO.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=%s does not exist", bandUserEditableDTO.getBandId())));
        User user = userRepository.findById(bandUserEditableDTO.getUserId())
                .orElseThrow(() -> new NoSuchUserException(String.format("User with id=%s does not exist", bandUserEditableDTO.getUserId())));

        if (!band.getUsers().contains(user)) {
            throw new BandUserConflictException(String.format("User with id=%s does not belong to the Band with id=%s", bandUserEditableDTO.getUserId(), bandUserEditableDTO.getBandId()));
        }

        band.getUsers().remove(user);

        return bandMapper.mapToBandDTO(bandRepository.save(band));
    }

    private Band configureBand(Band band, BandEditableDTO bandEditableDTO) {
        band.setName(bandEditableDTO.getName());

        return band;
    }
}
