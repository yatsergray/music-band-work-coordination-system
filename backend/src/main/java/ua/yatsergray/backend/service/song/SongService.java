package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongDTO;
import ua.yatsergray.backend.domain.request.song.SongCreateUpdateRequest;
import ua.yatsergray.backend.domain.request.song.SongKeyCreateRequest;
import ua.yatsergray.backend.exception.song.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongService {

    SongDTO addSong(SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException, NoSuchSongCategoryException;

    Optional<SongDTO> getSongById(UUID songId);

    List<SongDTO> getAllSongs();

    SongDTO modifySongById(UUID songId, SongCreateUpdateRequest songCreateUpdateRequest) throws NoSuchSongException, NoSuchKeyException, NoSuchArtistException, NoSuchTimeSignatureException, SongAlreadyExistsException, NoSuchSongCategoryException;

    void removeSongById(UUID songId) throws NoSuchSongException;

    SongDTO addSongKey(UUID songId, SongKeyCreateRequest songKeyCreateRequest) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException;

    void removeSongKey(UUID songId, UUID keyId) throws NoSuchSongException, NoSuchKeyException, SongKeyConflictException;
}
