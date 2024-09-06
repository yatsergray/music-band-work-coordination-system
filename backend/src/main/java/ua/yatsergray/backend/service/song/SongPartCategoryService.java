package ua.yatsergray.backend.service.song;

import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartCategoryEditableDTO;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongPartCategoryService {

    SongPartCategoryDTO addSongPartCategory(SongPartCategoryEditableDTO songPartCategoryEditableDTO);

    Optional<SongPartCategoryDTO> getSongPartCategoryById(UUID id);

    List<SongPartCategoryDTO> getAllSongPartCategories();

    SongPartCategoryDTO modifySongPartCategoryById(UUID id, SongPartCategoryEditableDTO songPartCategoryEditableDTO) throws NoSuchSongPartCategoryException;

    void removeSongPartCategoryById(UUID id) throws NoSuchSongPartCategoryException;
}
