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
        return songPartMapper.mapToSongPartDTO(songPartRepository.save(configureSongPart(new SongPart(), songPartEditableDTO)));
    }

    @Override
    public Optional<SongPartDTO> getSongPartById(UUID songPartId) {
        return songPartRepository.findById(songPartId).map(songPartMapper::mapToSongPartDTO);
    }

    @Override
    public List<SongPartDTO> getAllSongParts() {
        return songPartMapper.mapAllToSongPartDTOList(songPartRepository.findAll());
    }

    @Override
    public SongPartDTO modifySongPartById(UUID songPartId, SongPartEditableDTO songPartEditableDTO) throws NoSuchSongPartException, NoSuchSongException, NoSuchSongPartCategoryException {
        SongPart songPart = songPartRepository.findById(songPartId)
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=%s does not exist", songPartId)));

        return songPartMapper.mapToSongPartDTO(songPartRepository.save(configureSongPart(songPart, songPartEditableDTO)));
    }

    @Override
    public void removeSongPartById(UUID songPartId) throws NoSuchSongPartException {
        if (!songPartRepository.existsById(songPartId)) {
            throw new NoSuchSongPartException(String.format("Song part with id=%s does not exist", songPartId));
        }

        songPartRepository.deleteById(songPartId);
    }

    private SongPart configureSongPart(SongPart songPart, SongPartEditableDTO songPartEditableDTO) throws NoSuchSongException, NoSuchSongPartCategoryException {
        Song song = songRepository.findById(songPartEditableDTO.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=%s does not exist", songPartEditableDTO.getSongId())));
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartEditableDTO.getSongPartCategoryId())
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category with id=%s does not exist", songPartEditableDTO.getSongPartCategoryId())));

        songPart.setText(songPartEditableDTO.getText());
        songPart.setTypeNumber(songPartEditableDTO.getTypeNumber());
        songPart.setMeasuresNumber(songPartEditableDTO.getMeasuresNumber());
        songPart.setSong(song);
        songPart.setSongPartCategory(songPartCategory);

        return songPart;
    }
}
