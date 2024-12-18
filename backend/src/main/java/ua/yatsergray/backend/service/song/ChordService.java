package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.ChordDTO;
import ua.yatsergray.backend.domain.request.song.ChordCreateUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.ChordAlreadyExistsException;
import ua.yatsergray.backend.exception.song.NoSuchChordException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChordService {

    ChordDTO addChord(ChordCreateUpdateRequest chordCreateUpdateRequest) throws ChordAlreadyExistsException;

    Optional<ChordDTO> getChordById(UUID chordId);

    List<ChordDTO> getAllChords();

    ChordDTO modifyChordById(UUID chordId, ChordCreateUpdateRequest chordCreateUpdateRequest) throws NoSuchChordException, ChordAlreadyExistsException;

    void removeChordById(UUID chordId) throws NoSuchChordException, ChildEntityExistsException;
}
