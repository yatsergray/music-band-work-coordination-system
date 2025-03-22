package ua.yatsergray.backend.v2.service;

import org.springframework.data.domain.Page;
import ua.yatsergray.backend.v2.domain.dto.SongDTO;
import ua.yatsergray.backend.v2.domain.request.SongCreateRequest;
import ua.yatsergray.backend.v2.domain.request.SongUpdateRequest;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandException;
import ua.yatsergray.backend.v2.exception.NoSuchSongException;

import java.util.Optional;
import java.util.UUID;

public interface SongService {

    SongDTO addSong(SongCreateRequest songCreateRequest) throws NoSuchMusicBandException;

    Optional<SongDTO> getSongById(UUID songId);

//    List<SongDTO> getAllSongs();

//    Page<SongDTO> getAllSongsByPageAndSize(int page, int size);

    Page<SongDTO> getAllSongsByMusicBandIdAndPageAndSize(UUID musicBandId, int page, int size);

    SongDTO modifySongById(UUID songId, SongUpdateRequest songUpdateRequest) throws NoSuchSongException;

    void removeSongById(UUID songId) throws NoSuchSongException;
}
