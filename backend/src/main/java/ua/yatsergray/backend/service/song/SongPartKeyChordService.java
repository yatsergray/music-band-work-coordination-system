package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartKeyChordDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartKeyChordEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchChordException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartKeyChordException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartKeyChordService {

    SongPartKeyChordDTO addSongPartKeyChord(SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) throws NoSuchKeyException, NoSuchChordException, NoSuchSongPartException;

    Optional<SongPartKeyChordDTO> getSongPartKeyChordById(UUID id);

    List<SongPartKeyChordDTO> getAllSongPartKeyChords();

    SongPartKeyChordDTO modifySongPartKeyChordById(UUID id, SongPartKeyChordEditableDTO songPartKeyChordEditableDTO) throws NoSuchSongPartKeyChordException, NoSuchKeyException, NoSuchChordException, NoSuchSongPartException;

    void removeSongPartKeyChordById(UUID id) throws NoSuchSongPartKeyChordException;
}
