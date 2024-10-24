package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartCategoryDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartCategoryEditableDTO;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.SongPartCategoryAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.SongPartCategoryMapper;
import ua.yatsergray.backend.repository.song.SongPartCategoryRepository;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.service.song.SongPartCategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SongPartCategoryServiceImpl implements SongPartCategoryService {
    private final SongPartCategoryMapper songPartCategoryMapper;
    private final SongPartCategoryRepository songPartCategoryRepository;
    private final SongPartRepository songPartRepository;

    @Autowired
    public SongPartCategoryServiceImpl(SongPartCategoryMapper songPartCategoryMapper, SongPartCategoryRepository songPartCategoryRepository, SongPartRepository songPartRepository) {
        this.songPartCategoryMapper = songPartCategoryMapper;
        this.songPartCategoryRepository = songPartCategoryRepository;
        this.songPartRepository = songPartRepository;
    }

    @Override
    public SongPartCategoryDTO addSongPartCategory(SongPartCategoryEditableDTO songPartCategoryEditableDTO) throws SongPartCategoryAlreadyExistsException {
        return songPartCategoryMapper.mapToSongPartCategoryDTO(songPartCategoryRepository.save(cofigureSongPartCategory(new SongPartCategory(), songPartCategoryEditableDTO)));
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
    public SongPartCategoryDTO modifySongPartCategoryById(UUID songPartCategoryId, SongPartCategoryEditableDTO songPartCategoryEditableDTO) throws NoSuchSongPartCategoryException, SongPartCategoryAlreadyExistsException {
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartCategoryId)
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category with id=\"%s\" does not exist", songPartCategoryId)));

        return songPartCategoryMapper.mapToSongPartCategoryDTO(songPartCategoryRepository.save(cofigureSongPartCategory(songPartCategory, songPartCategoryEditableDTO)));
    }

    @Override
    public void removeSongPartCategoryById(UUID songPartCategoryId) throws NoSuchSongPartCategoryException, ChildEntityExistsException {
        if (songPartCategoryRepository.existsById(songPartCategoryId)) {
            throw new NoSuchSongPartCategoryException(String.format("Song part category with id=\"%s\" does not exist", songPartCategoryId));
        }

        checkIfSongPartCategoryHasChildEntity(songPartCategoryId);

        songPartCategoryRepository.deleteById(songPartCategoryId);
    }

    private SongPartCategory cofigureSongPartCategory(SongPartCategory songPartCategory, SongPartCategoryEditableDTO songPartCategoryEditableDTO) throws SongPartCategoryAlreadyExistsException {
        if (Objects.isNull(songPartCategory.getId())) {
            if (songPartCategoryRepository.existsByName(songPartCategoryEditableDTO.getName())) {
                throw new SongPartCategoryAlreadyExistsException(String.format("Song part category with name=\"%s\" already exists", songPartCategoryEditableDTO.getName()));
            }

            if (songPartCategoryRepository.existsByType(songPartCategoryEditableDTO.getType())) {
                throw new SongPartCategoryAlreadyExistsException(String.format("Song part category with type=\"%s\" already exists", songPartCategoryEditableDTO.getType()));
            }
        } else {
            if (!songPartCategoryEditableDTO.getName().equals(songPartCategory.getName()) && songPartCategoryRepository.existsByName(songPartCategoryEditableDTO.getName())) {
                throw new SongPartCategoryAlreadyExistsException(String.format("Song part category with name=\"%s\" already exists", songPartCategoryEditableDTO.getName()));
            }

            if (!songPartCategoryEditableDTO.getType().equals(songPartCategory.getType()) && songPartCategoryRepository.existsByType(songPartCategoryEditableDTO.getType())) {
                throw new SongPartCategoryAlreadyExistsException(String.format("Song part category with type=\"%s\" already exists", songPartCategoryEditableDTO.getType()));
            }
        }

        songPartCategory.setName(songPartCategoryEditableDTO.getName());
        songPartCategory.setType(songPartCategoryEditableDTO.getType());

        return songPartCategory;
    }

    private void checkIfSongPartCategoryHasChildEntity(UUID songPartCategoryId) throws ChildEntityExistsException {
        long songPartCategoryChildEntityAmount = songPartRepository.countBySongPartCategoryId(songPartCategoryId);

        if (songPartCategoryChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%d Song part(s) depend(s) on the Song part category with id=%s", songPartCategoryChildEntityAmount, songPartCategoryId));
        }
    }
}
