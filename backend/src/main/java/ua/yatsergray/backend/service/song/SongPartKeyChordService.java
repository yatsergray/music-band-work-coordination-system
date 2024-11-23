package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.request.song.SongPartKeyChordCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartKeyChordUpdateRequest;
import ua.yatsergray.backend.exception.song.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartKeyChordService {

    SongPartKeyChordDTO addSongPartKeyChord(SongPartKeyChordCreateRequest songPartKeyChordCreateRequest) throws NoSuchKeyException, NoSuchChordException, NoSuchSongPartException, SongPartKeyChordAlreadyExistsException, SongPartKeyChordConflictException;

    Optional<SongPartKeyChordDTO> getSongPartKeyChordById(UUID songPartKeyChordId);

    List<SongPartKeyChordDTO> getAllSongPartKeyChords();

    SongPartKeyChordDTO modifySongPartKeyChordById(UUID songPartKeyChordId, SongPartKeyChordUpdateRequest songPartKeyChordUpdateRequest) throws NoSuchSongPartKeyChordException, NoSuchKeyException, NoSuchChordException, NoSuchSongPartException, SongPartKeyChordAlreadyExistsException, SongPartKeyChordConflictException;

    void removeSongPartKeyChordById(UUID songPartKeyChordId) throws NoSuchSongPartKeyChordException;
}
