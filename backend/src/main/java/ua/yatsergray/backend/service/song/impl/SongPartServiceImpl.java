package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.mapper.song.SongPartMapper;
import ua.yatsergray.backend.repository.song.SongPartCategoryRepository;
import ua.yatsergray.backend.repository.song.SongPartRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongPartService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongPartServiceImpl implements SongPartService {
    private final SongPartMapper songPartMapper;
    private final SongPartRepository songPartRepository;
    private final SongRepository songRepository;
    private final SongPartCategoryRepository songPartCategoryRepository;

    @Autowired
    public SongPartServiceImpl(SongPartMapper songPartMapper, SongPartRepository songPartRepository, SongRepository songRepository, SongPartCategoryRepository songPartCategoryRepository) {
        this.songPartMapper = songPartMapper;
        this.songPartRepository = songPartRepository;
        this.songRepository = songRepository;
        this.songPartCategoryRepository = songPartCategoryRepository;
    }

    @Override
    public SongPartDTO addSongPart(SongPartEditableDTO songPartEditableDTO) throws NoSuchSongException, NoSuchSongPartCategoryException {
        Song song = songRepository.findById(songPartEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", songPartEditableDTO.getSongUUID())));
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartEditableDTO.getSongPartCategoryUUID())
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category does not exist with id=%s", songPartEditableDTO.getSongPartCategoryUUID())));

        SongPart songPart = SongPart.builder()
                .text(songPartEditableDTO.getText())
                .typeNumber(songPartEditableDTO.getTypeNumber())
                .measuresNumber(songPartEditableDTO.getMeasuresNumber())
                .song(song)
                .songPartCategory(songPartCategory)
                .build();

        return songPartMapper.mapToSongPartDTO(songPartRepository.save(songPart));
    }

    @Override
    public Optional<SongPartDTO> getSongPartById(UUID id) {
        return songPartRepository.findById(id).map(songPartMapper::mapToSongPartDTO);
    }

    @Override
    public List<SongPartDTO> getAllSongParts() {
        return songPartMapper.mapAllToSongPartDTOList(songPartRepository.findAll());
    }

    @Override
    public SongPartDTO modifySongPartById(UUID id, SongPartEditableDTO songPartEditableDTO) throws NoSuchSongPartException, NoSuchSongException, NoSuchSongPartCategoryException {
        SongPart songPart = songPartRepository.findById(id)
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part does not exist with id=%s", id)));
        Song song = songRepository.findById(songPartEditableDTO.getSongUUID())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song does not exist with id=%s", songPartEditableDTO.getSongUUID())));
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartEditableDTO.getSongPartCategoryUUID())
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category does not exist with id=%s", songPartEditableDTO.getSongPartCategoryUUID())));

        songPart.setText(songPartEditableDTO.getText());
        songPart.setTypeNumber(songPartEditableDTO.getTypeNumber());
        songPart.setMeasuresNumber(songPartEditableDTO.getMeasuresNumber());
        songPart.setSong(song);
        songPart.setSongPartCategory(songPartCategory);

        return songPartMapper.mapToSongPartDTO(songPartRepository.save(songPart));
    }

    @Override
    public void removeSongPartById(UUID id) throws NoSuchSongPartException {
        if (!songPartRepository.existsById(id)) {
            throw new NoSuchSongPartException(String.format("Song part does not exist with id=%s", id));
        }

        songPartRepository.deleteById(id);
    }
}
