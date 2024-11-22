package ua.yatsergray.backend.service.song.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongPartDTO;
import ua.yatsergray.backend.domain.entity.song.Song;
import ua.yatsergray.backend.domain.entity.song.SongPart;
import ua.yatsergray.backend.domain.entity.song.SongPartCategory;
import ua.yatsergray.backend.domain.entity.song.SongPartKeyChord;
import ua.yatsergray.backend.domain.request.song.SongPartCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongPartUpdateRequest;
import ua.yatsergray.backend.exception.song.NoSuchSongException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartCategoryException;
import ua.yatsergray.backend.exception.song.NoSuchSongPartException;
import ua.yatsergray.backend.exception.song.SongPartAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.SongPartMapper;
import ua.yatsergray.backend.repository.song.*;
import ua.yatsergray.backend.service.song.SongPartService;

import java.util.List;
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
    public SongPartDTO addSongPart(SongPartCreateRequest songPartCreateRequest) throws NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException {
        Song song = songRepository.findById(songPartCreateRequest.getSongId())
                .orElseThrow(() -> new NoSuchSongException(String.format("Song with id=\"%s\" does not exist", songPartCreateRequest.getSongId())));
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartCreateRequest.getSongPartCategoryId())
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category with id=\"%s\" does not exist", songPartCreateRequest.getSongPartCategoryId())));

        if (songPartRepository.existsBySongIdAndSongPartCategoryIdAndTypeNumber(songPartCreateRequest.getSongId(), songPartCreateRequest.getSongPartCategoryId(), songPartCreateRequest.getTypeNumber())) {
            throw new SongPartAlreadyExistsException(String.format("Song part with songId=\"%s\", songPartCategoryId=\"%s\" and typeNumber=\"%s\" already exists", songPartCreateRequest.getSongId(), songPartCreateRequest.getSongPartCategoryId(), songPartCreateRequest.getTypeNumber()));
        }

        SongPart songPart = SongPart.builder()
                .text(songPartCreateRequest.getText())
                .typeNumber(songPartCreateRequest.getTypeNumber())
                .measuresNumber(songPartCreateRequest.getMeasuresNumber())
                .song(song)
                .songPartCategory(songPartCategory)
                .build();

        return songPartMapper.mapToSongPartDTO(songPartRepository.save(songPart));
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
    public SongPartDTO modifySongPartById(UUID songPartId, SongPartUpdateRequest songPartUpdateRequest) throws NoSuchSongPartException, NoSuchSongException, NoSuchSongPartCategoryException, SongPartAlreadyExistsException {
        SongPart songPart = songPartRepository.findById(songPartId)
                .orElseThrow(() -> new NoSuchSongPartException(String.format("Song part with id=\"%s\" does not exist", songPartId)));
        SongPartCategory songPartCategory = songPartCategoryRepository.findById(songPartUpdateRequest.getSongPartCategoryId())
                .orElseThrow(() -> new NoSuchSongPartCategoryException(String.format("Song part category with id=\"%s\" does not exist", songPartUpdateRequest.getSongPartCategoryId())));

        if ((!songPartUpdateRequest.getSongPartCategoryId().equals(songPart.getSongPartCategory().getId()) || !songPartUpdateRequest.getTypeNumber().equals(songPart.getTypeNumber())) && songPartRepository.existsBySongIdAndSongPartCategoryIdAndTypeNumber(songPart.getSong().getId(), songPartUpdateRequest.getSongPartCategoryId(), songPartUpdateRequest.getTypeNumber())) {
            throw new SongPartAlreadyExistsException(String.format("Song part with songId=\"%s\", songPartCategoryId=\"%s\" and typeNumber=\"%s\" already exists", songPart.getSong().getId(), songPartUpdateRequest.getSongPartCategoryId(), songPartUpdateRequest.getTypeNumber()));
        }

        songPart.setText(songPartUpdateRequest.getText());
        songPart.setTypeNumber(songPartUpdateRequest.getTypeNumber());
        songPart.setMeasuresNumber(songPartUpdateRequest.getMeasuresNumber());
        songPart.setSongPartCategory(songPartCategory);

        return songPartMapper.mapToSongPartDTO(songPartRepository.save(songPart));
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
}
