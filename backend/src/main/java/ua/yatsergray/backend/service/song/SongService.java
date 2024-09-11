package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchArtistException;
import ua.yatsergray.backend.exception.song.NoSuchKeyException;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchTimeSignatureException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {

    SongDTO addSong(SongEditableDTO songEditableDTO) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException;

    Optional<SongDTO> getSongById(UUID songId);

    List<SongDTO> getAllSongs();

    SongDTO modifySongById(UUID songId, SongEditableDTO songEditableDTO) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException;

    void removeSongById(UUID songId) throws NoSuchSongException;
}
