package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.request.song.SongCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongKeyCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongUpdateRequest;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {

    SongDTO addSong(SongCreateRequest songCreateRequest) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException, NoSuchSongCategoryException, NoSuchSongMoodException, NoSuchBandException;

    Optional<SongDTO> getSongById(UUID songId);

    List<SongDTO> getAllSongs();

    SongDTO modifySongById(UUID songId, SongUpdateRequest songUpdateRequest) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, NoSuchSongCategoryException, NoSuchSongMoodException, SongAlreadyExistsException;

    void removeSongById(UUID songId) throws NoSuchSongException;

    SongDTO addSongKey(UUID songId, SongKeyCreateRequest songKeyCreateRequest) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException;

    void removeSongKey(UUID songId, UUID keyId) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException;
}
