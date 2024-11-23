package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.request.song.SongPartCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartCategoryUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.SongPartCategoryAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartCategoryService {

    SongPartCategoryDTO addSongPartCategory(SongPartCategoryCreateRequest songPartCategoryCreateRequest) throws SongPartCategoryAlreadyExistsException;

    Optional<SongPartCategoryDTO> getSongPartCategoryById(UUID songPartCategoryId);

    List<SongPartCategoryDTO> getAllSongPartCategories();

    SongPartCategoryDTO modifySongPartCategoryById(UUID songPartCategoryId, SongPartCategoryUpdateRequest songPartCategoryUpdateRequest) throws NoSuchSongPartCategoryException, SongPartCategoryAlreadyExistsException;

    void removeSongPartCategoryById(UUID songPartCategoryId) throws NoSuchSongPartCategoryException, ChildEntityExistsException;
}
