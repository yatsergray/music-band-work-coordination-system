package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.ChordEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchChordException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChordService {

    ChordDTO addChord(ChordEditableDTO chordEditableDTO);

    Optional<ChordDTO> getChordById(UUID id);

    List<ChordDTO> getAllChords();

    ChordDTO modifyChordById(UUID id, ChordEditableDTO chordEditableDTO) throws NoSuchChordException;

    void removeChordById(UUID id) throws NoSuchChordException;
}
