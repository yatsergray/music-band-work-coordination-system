package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.TimeSignatureDTO;
import ua.yatsergray.backend.domain.dto.song.editable.TimeSignatureEditableDTO;
import ua.yatsergray.backend.domain.entity.song.TimeSignature;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;
import ua.yatsergray.backend.mapper.song.TimeSignatureMapper;
import ua.yatsergray.backend.repository.song.TimeSignatureRepository;
import ua.yatsergray.backend.service.song.TimeSignatureService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TimeSignatureServiceImpl implements TimeSignatureService {
    private final TimeSignatureMapper timeSignatureMapper;
    private final TimeSignatureRepository timeSignatureRepository;

    @Autowired
    public TimeSignatureServiceImpl(TimeSignatureMapper timeSignatureMapper, TimeSignatureRepository timeSignatureRepository) {
        this.timeSignatureMapper = timeSignatureMapper;
        this.timeSignatureRepository = timeSignatureRepository;
    }

    @Override
    public TimeSignatureDTO addTimeSignature(TimeSignatureEditableDTO timeSignatureEditableDTO) {
        TimeSignature timeSignature = TimeSignature.builder()
                .beats(timeSignatureEditableDTO.getBeats())
                .duration(timeSignatureEditableDTO.getDuration())
                .build();

        return timeSignatureMapper.mapToTimeSignatureDTO(timeSignatureRepository.save(timeSignature));
    }

    @Override
    public Optional<TimeSignatureDTO> getTimeSignatureById(UUID id) {
        return timeSignatureRepository.findById(id).map(timeSignatureMapper::mapToTimeSignatureDTO);
    }

    @Override
    public List<TimeSignatureDTO> getAllTimeSignatures() {
        return timeSignatureMapper.mapAllToTimeSignatureDTOList(timeSignatureRepository.findAll());
    }

    @Override
    public TimeSignatureDTO modifyTimeSignatureById(UUID id, TimeSignatureEditableDTO timeSignatureEditableDTO) throws NoSuchTimeSignatureException {
        return timeSignatureRepository.findById(id)
                .map(timeSignature -> {
                    timeSignature.setBeats(timeSignatureEditableDTO.getBeats());
                    timeSignature.setDuration(timeSignatureEditableDTO.getDuration());

                    return timeSignatureMapper.mapToTimeSignatureDTO(timeSignatureRepository.save(timeSignature));
                })
                .orElseThrow(() -> new NoSuchTimeSignatureException(String.format("Time signature does not exist with id=%s", id)));
    }

    @Override
    public void removeTimeSignatureById(UUID id) throws NoSuchTimeSignatureException {
        if (!timeSignatureRepository.existsById(id)) {
            throw new NoSuchTimeSignatureException(String.format("Time signature does not exist with id=%s", id));
        }

        timeSignatureRepository.deleteById(id);
    }
}
