package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.dto.song.editable.TimeSignatureEditableDTO;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;
import ua.yatsergray.backend.exception.song.TimeSignatureAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.TimeSignatureMapper;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.repository.song.TimeSignatureRepository;
import ua.yatsergray.backend.service.song.TimeSignatureService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class TimeSignatureServiceImpl implements TimeSignatureService {
    private final TimeSignatureMapper timeSignatureMapper;
    private final TimeSignatureRepository timeSignatureRepository;
    private final SongRepository songRepository;

    @Autowired
    public TimeSignatureServiceImpl(TimeSignatureMapper timeSignatureMapper, TimeSignatureRepository timeSignatureRepository, SongRepository songRepository) {
        this.timeSignatureMapper = timeSignatureMapper;
        this.timeSignatureRepository = timeSignatureRepository;
        this.songRepository = songRepository;
    }

    @Override
    public TimeSignatureDTO addTimeSignature(TimeSignatureEditableDTO timeSignatureEditableDTO) throws TimeSignatureAlreadyExistsException {
        return timeSignatureMapper.mapToTimeSignatureDTO(timeSignatureRepository.save(configureTimeSignature(new TimeSignature(), timeSignatureEditableDTO)));
    }

    @Override
    public Optional<TimeSignatureDTO> getTimeSignatureById(UUID timeSignatureId) {
        return timeSignatureRepository.findById(timeSignatureId).map(timeSignatureMapper::mapToTimeSignatureDTO);
    }

    @Override
    public List<TimeSignatureDTO> getAllTimeSignatures() {
        return timeSignatureMapper.mapAllToTimeSignatureDTOList(timeSignatureRepository.findAll());
    }

    @Override
    public TimeSignatureDTO modifyTimeSignatureById(UUID timeSignatureId, TimeSignatureEditableDTO timeSignatureEditableDTO) throws NoSuchTimeSignatureException, TimeSignatureAlreadyExistsException {
        TimeSignature timeSignature = timeSignatureRepository.findById(timeSignatureId)
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature with id=\"%s\" does not exist", timeSignatureId)));

        return timeSignatureMapper.mapToTimeSignatureDTO(timeSignatureRepository.save(configureTimeSignature(timeSignature, timeSignatureEditableDTO)));
    }

    @Override
    public void removeTimeSignatureById(UUID timeSignatureId) throws NoSuchTimeSignatureException, ChildEntityExistsException {
        if (!timeSignatureRepository.existsById(timeSignatureId)) {
            throw new NoSuchTimeSignatureException(String.format("Time signature with id=\"%s\" does not exist", timeSignatureId));
        }

        checkIfTimeSignatureHasChildEntity(timeSignatureId);

        timeSignatureRepository.deleteById(timeSignatureId);
    }

    private TimeSignature configureTimeSignature(TimeSignature timeSignature, TimeSignatureEditableDTO timeSignatureEditableDTO) throws TimeSignatureAlreadyExistsException {
        if (Objects.isNull(timeSignature.getId())) {
            if (timeSignatureRepository.existsByBeatsAndDuration(timeSignatureEditableDTO.getBeats(), timeSignatureEditableDTO.getDuration())) {
                throw new TimeSignatureAlreadyExistsException(String.format("Time signature with beats=\"%s\" and duration=\"%s\" already exists", timeSignatureEditableDTO.getBeats(), timeSignatureEditableDTO.getDuration()));
            }
        } else {
            if ((!timeSignatureEditableDTO.getBeats().equals(timeSignature.getBeats()) || !timeSignatureEditableDTO.getDuration().equals(timeSignature.getDuration())) && timeSignatureRepository.existsByBeatsAndDuration(timeSignatureEditableDTO.getBeats(), timeSignatureEditableDTO.getDuration())) {
                throw new TimeSignatureAlreadyExistsException(String.format("Time signature with beats=\"%s\" and duration=\"%s\" already exists", timeSignatureEditableDTO.getBeats(), timeSignatureEditableDTO.getDuration()));
            }
        }

        timeSignature.setBeats(timeSignatureEditableDTO.getBeats());
        timeSignature.setDuration(timeSignatureEditableDTO.getDuration());

        return timeSignature;
    }

    private void checkIfTimeSignatureHasChildEntity(UUID timeSignatureId) throws ChildEntityExistsException {
        long timeSignatureChildEntityAmount = songRepository.countByTimeSignatureId(timeSignatureId);

        if (timeSignatureChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song(s) depend(s) on the Time signature with id=%s", timeSignatureChildEntityAmount, timeSignatureId));
        }
    }
}
