package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartCategoryEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.mapper.song.SongPartCategoryMapper;
import ua.yatsergray.backend.repository.song.SongPartCategoryRepository;
import ua.yatsergray.backend.service.song.SongPartCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongPartCategoryServiceImpl implements SongPartCategoryService {
    private final SongPartCategoryMapper songPartCategoryMapper;
    private final SongPartCategoryRepository songPartCategoryRepository;

    @Autowired
    public SongPartCategoryServiceImpl(SongPartCategoryMapper songPartCategoryMapper, SongPartCategoryRepository songPartCategoryRepository) {
        this.songPartCategoryMapper = songPartCategoryMapper;
        this.songPartCategoryRepository = songPartCategoryRepository;
    }

    @Override
    public SongPartCategoryDTO addSongPartCategory(SongPartCategoryEditableDTO songPartCategoryEditableDTO) {
        SongPartCategory songPartCategory = SongPartCategory.builder()
                .name(songPartCategoryEditableDTO.getName())
                .type(songPartCategoryEditableDTO.getType())
                .build();

        return songPartCategoryMapper.mapToSongPartCategoryDTO(songPartCategoryRepository.save(songPartCategory));
    }

    @Override
    public Optional<SongPartCategoryDTO> getSongPartCategoryById(UUID songPartCategoryId) {
        return songPartCategoryRepository.findById(songPartCategoryId).map(songPartCategoryMapper::mapToSongPartCategoryDTO);
    }

    @Override
    public List<SongPartCategoryDTO> getAllSongPartCategories() {
        return songPartCategoryMapper.mapAllToSongPartCategoryDTOList(songPartCategoryRepository.findAll());
    }

    @Override
    public SongPartCategoryDTO modifySongPartCategoryById(UUID songPartCategoryId, SongPartCategoryEditableDTO songPartCategoryEditableDTO) throws NoSuchSongPartCategoryException {
        return songPartCategoryRepository.findById(songPartCategoryId)
                .map(songPartCategory -> {
                    songPartCategory.setName(songPartCategoryEditableDTO.getName());
                    songPartCategory.setType(songPartCategoryEditableDTO.getType());

                    return songPartCategoryMapper.mapToSongPartCategoryDTO(songPartCategoryRepository.save(songPartCategory));
                })
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category does not exist with id=%s", songPartCategoryId)));
    }

    @Override
    public void removeSongPartCategoryById(UUID songPartCategoryId) throws NoSuchSongPartCategoryException {
        if (songPartCategoryRepository.existsById(songPartCategoryId)) {
            throw new NoSuchSongPartCategoryException(String.format("Song part category does not exist with id=%s", songPartCategoryId));
        }

        songPartCategoryRepository.deleteById(songPartCategoryId);
    }
}
