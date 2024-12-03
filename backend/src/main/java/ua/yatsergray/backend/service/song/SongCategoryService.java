package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongCategoryDTO;
import ua.yatsergray.backend.domain.request.song.SongCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongCategoryUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.NoSuchSongCategoryException;
import ua.yatsergray.backend.exception.song.SongCategoryAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongCategoryService {

    SongCategoryDTO addSongCategory(SongCategoryCreateRequest songCategoryCreateRequest) throws NoSuchBandException, SongCategoryAlreadyExistsException;

    Optional<SongCategoryDTO> getSongCategoryById(UUID songCategoryId);

    List<SongCategoryDTO> getAllSongCategories();

    SongCategoryDTO modifySongCategoryById(UUID songCategoryId, SongCategoryUpdateRequest songCategoryUpdateRequest) throws NoSuchSongCategoryException, SongCategoryAlreadyExistsException;

    void removeSongCategoryById(UUID songCategoryId) throws NoSuchSongCategoryException, ChildEntityExistsException;
}
