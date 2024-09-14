package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ChordEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Chord;
import ua.yatsergray.backend.exception.song.NoSuchChordException;
import ua.yatsergray.backend.mapper.song.ChordMapper;
import ua.yatsergray.backend.repository.band.ChordRepository;
import ua.yatsergray.backend.service.song.ChordService;

import java.util.List;
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
    public ChordDTO addChord(ChordEditableDTO chordEditableDTO) {
        Chord chord = Chord.builder()
                .name(chordEditableDTO.getName())
                .build();

        return chordMapper.mapToChordDTO(chordRepository.save(chord));
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
    public ChordDTO modifyChordById(UUID chordId, ChordEditableDTO chordEditableDTO) throws NoSuchChordException {
        return chordRepository.findById(chordId)
                .map(chord -> {
                    chord.setName(chordEditableDTO.getName());

                    return chordMapper.mapToChordDTO(chordRepository.save(chord));
                })
                .orElseThrow(() -> new NoSuchChordException(String.format("Chord with id=%s not exist", chordId)));
    }

    @Override
    public void removeChordById(UUID chordId) throws NoSuchChordException {
        if (!chordRepository.existsById(chordId)) {
            throw new NoSuchChordException(String.format("Chord with id=%s does not exist", chordId));
        }

        chordRepository.deleteById(chordId);
    }
}
