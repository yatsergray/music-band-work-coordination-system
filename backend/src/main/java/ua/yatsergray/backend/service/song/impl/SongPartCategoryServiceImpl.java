package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartCategoryEditableDTO;
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
        return songPartCategoryMapper.mapToSongPartCategoryDTO(songPartCategoryRepository.save(songPartCategoryMapper.mapToSongPartCategory(songPartCategoryEditableDTO)));
    }

    @Override
    public Optional<SongPartCategoryDTO> getSongPartCategoryById(UUID id) {
        return songPartCategoryRepository.findById(id).map(songPartCategoryMapper::mapToSongPartCategoryDTO);
    }

    @Override
    public List<SongPartCategoryDTO> getAllSongPartCategories() {
        return songPartCategoryMapper.mapAllToSongPartCategoryDTOList(songPartCategoryRepository.findAll());
    }

    @Override
    public SongPartCategoryDTO modifySongPartCategoryById(UUID id, SongPartCategoryEditableDTO songPartCategoryEditableDTO) throws NoSuchSongPartCategoryException {
        return songPartCategoryRepository.findById(id)
                .map(songPartCategory -> {
                    songPartCategory.setName(songPartCategoryEditableDTO.getName());
                    songPartCategory.setType(songPartCategoryEditableDTO.getType());

                    return songPartCategoryMapper.mapToSongPartCategoryDTO(songPartCategoryRepository.save(songPartCategory));
                })
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category does not exist with id=%s", id)));
    }

    @Override
    public void removeSongPartCategoryById(UUID id) throws NoSuchSongPartCategoryException {
        if (songPartCategoryRepository.existsById(id)) {
            throw new NoSuchSongPartCategoryException(String.format("Song part category does not exist with id=%s", id));
        }

        songPartCategoryRepository.deleteById(id);
    }
}
