package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.dto.song.editable.SongPartEditableDTO;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.SongPartMapper;
import ua.yatsergray.backend.repository.song.*;
import ua.yatsergray.backend.service.song.SongPartService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SongPartServiceImpl implements SongPartService {
    private final SongPartMapper songPartMapper;
    private final SongPartRepository songPartRepository;
    private final SongRepository songRepository;
    private final SongPartCategoryRepository songPartCategoryRepository;
    private final SongPartDetailsRepository songPartDetailsRepository;
    private final SongPartKeyChordRepository songPartKeyChordRepository;

    @Autowired
    public SongPartServiceImpl(SongPartMapper songPartMapper, SongPartRepository songPartRepository, SongRepository songRepository, SongPartCategoryRepository songPartCategoryRepository, SongPartDetailsRepository songPartDetailsRepository, SongPartKeyChordRepository songPartKeyChordRepository) {
        this.songPartMapper = songPartMapper;
        this.songPartRepository = songPartRepository;
        this.songRepository = songRepository;
        this.songPartCategoryRepository = songPartCategoryRepository;
        this.songPartDetailsRepository = songPartDetailsRepository;
        this.songPartKeyChordRepository = songPartKeyChordRepository;
    }

    @Override
    public SongPartDTO addSongPart(SongPartEditableDTO songPartEditableDTO) throws NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException {
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
    public SongPartDTO modifySongPartById(UUID songPartId, SongPartEditableDTO songPartEditableDTO) throws NoSuchSongPartException, NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException {
        SongPart songPart = songPartRepository.findById(songPartId)
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=\"%s\" does not exist", songPartId)));

        return songPartMapper.mapToSongPartDTO(songPartRepository.save(configureSongPart(songPart, songPartEditableDTO)));
    }

    @Override
    public void removeSongPartById(UUID songPartId) throws NoSuchSongPartException {
        SongPart songPart = songPartRepository.findById(songPartId)
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=\"%s\" does not exist", songPartId)));

        if (songPartRepository.isNotAvailableToDelete(songPartId)) {
            List<SongPartKeyChord> availableToDeleteSongPartKeyChords = songPartKeyChordRepository.findAvailableToDeleteBySongPartId(songPartId);

            songPartDetailsRepository.deleteBySongIdAndSongPartId(songPart.getSong().getId(), songPartId);
            songPartKeyChordRepository.deleteAll(availableToDeleteSongPartKeyChords);

            songPart.setSong(null);

            songPartRepository.save(songPart);
        } else {
            songPartDetailsRepository.deleteBySongIdAndSongPartId(songPart.getSong().getId(), songPartId);
            songPartRepository.deleteById(songPartId);
        }
    }

    private SongPart configureSongPart(SongPart songPart, SongPartEditableDTO songPartEditableDTO) throws NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException {
        Song song = songRepository.findById(songPartEditableDTO.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songPartEditableDTO.getSongId())));
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartEditableDTO.getSongPartCategoryId())
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category with id=\"%s\" does not exist", songPartEditableDTO.getSongPartCategoryId())));

        if (Objects.isNull(songPart.getId())) {
            if (songPartRepository.existsBySongIdAndSongPartCategoryIdAndTypeNumber(songPartEditableDTO.getSongId(), songPartEditableDTO.getSongPartCategoryId(), songPartEditableDTO.getTypeNumber())) {
                throw new SongPartAlreadyExistsException(String.format("Song part with songId=\"%s\", songPartCategoryId=\"%s\" and typeNumber=\"%s\" already exists", songPartEditableDTO.getSongId(), songPartEditableDTO.getSongPartCategoryId(), songPartEditableDTO.getTypeNumber()));
            }
        } else {
            if ((!songPartEditableDTO.getSongId().equals(songPart.getSong().getId()) || !songPartEditableDTO.getSongPartCategoryId().equals(songPart.getSongPartCategory().getId()) || !songPartEditableDTO.getTypeNumber().equals(songPart.getTypeNumber())) && songPartRepository.existsBySongIdAndSongPartCategoryIdAndTypeNumber(songPartEditableDTO.getSongId(), songPartEditableDTO.getSongPartCategoryId(), songPartEditableDTO.getTypeNumber())) {
                throw new SongPartAlreadyExistsException(String.format("Song part with songId=\"%s\", songPartCategoryId=\"%s\" and typeNumber=\"%s\" already exists", songPartEditableDTO.getSongId(), songPartEditableDTO.getSongPartCategoryId(), songPartEditableDTO.getTypeNumber()));
            }
        }

        songPart.setText(songPartEditableDTO.getText());
        songPart.setTypeNumber(songPartEditableDTO.getTypeNumber());
        songPart.setMeasuresNumber(songPartEditableDTO.getMeasuresNumber());
        songPart.setSong(song);
        songPart.setSongPartCategory(songPartCategory);

        return songPart;
    }
}
