package ua.yatsergray.backend.v2.service;

import ua.yatsergray.backend.v2.domain.dto.SongDTO;
import ua.yatsergray.backend.v2.domain.request.SongCreateUpdateRequest;
import ua.yatsergray.backend.v2.exception.NoSuchMusicBandException;
import ua.yatsergray.backend.v2.exception.NoSuchSongException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {

    SongDTO addSong(SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchMusicBandException;

    Optional<SongDTO> getSongById(UUID songId);

    List<SongDTO> getAllSongs();

    SongDTO modifySongById(UUID songId, SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchSongException;

    void removeSongById(UUID songId) throws NoSuchSongException;
}
