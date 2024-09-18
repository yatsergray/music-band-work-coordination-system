package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ChordEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Chord;
import ua.yatsergray.backend.exception.song.ChordAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchChordException;
import ua.yatsergray.backend.mapper.song.ChordMapper;
import ua.yatsergray.backend.repository.song.ChordRepository;
import ua.yatsergray.backend.service.song.ChordService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChordServiceImpl implements ChordService {
    private final ChordMapper chordMapper;
    private final ChordRepository chordRepository;

    @Autowired
    public ChordServiceImpl(ChordMapper chordMapper, ChordRepository chordRepository) {
        this.chordMapper = chordMapper;
        this.chordRepository = chordRepository;
    }

    @Override
    public ChordDTO addChord(ChordEditableDTO chordEditableDTO) throws ChordAlreadyExistsException {
        return chordMapper.mapToChordDTO(configureChord(new Chord(), chordEditableDTO));
    }

    @Override
    public Optional<ChordDTO> getChordById(UUID chordId) {
        return chordRepository.findById(chordId).map(chordMapper::mapToChordDTO);
    }

    @Override
    public List<ChordDTO> getAllChords() {
        return chordMapper.mapAllToChordDTOList(chordRepository.findAll());
    }

    @Override
    public ChordDTO modifyChordById(UUID chordId, ChordEditableDTO chordEditableDTO) throws NoSuchChordException, ChordAlreadyExistsException {
        Chord chord = chordRepository.findById(chordId)
                .orElseThrow(() -> new NoSuchChordException(String.format("Chord with id=%s not exist", chordId)));

        return chordMapper.mapToChordDTO(configureChord(chord, chordEditableDTO));
    }

    @Override
    public void removeChordById(UUID chordId) throws NoSuchChordException {
        if (!chordRepository.existsById(chordId)) {
            throw new NoSuchChordException(String.format("Chord with id=%s does not exist", chordId));
        }

        chordRepository.deleteById(chordId);
    }

    private Chord configureChord(Chord chord, ChordEditableDTO chordEditableDTO) throws ChordAlreadyExistsException {
        if (Objects.isNull(chord.getId())) {
            if (chordRepository.existsByName(chordEditableDTO.getName())) {
                throw new ChordAlreadyExistsException(String.format("Chord with name=%s already exists", chordEditableDTO.getName()));
            }
        } else {
            if (!chordEditableDTO.getName().equals(chord.getName()) && chordRepository.existsByName(chordEditableDTO.getName())) {
                throw new ChordAlreadyExistsException(String.format("Chord with name=%s already exists", chordEditableDTO.getName()));
            }
        }

        chord.setName(chordEditableDTO.getName());

        return chord;
    }
}
