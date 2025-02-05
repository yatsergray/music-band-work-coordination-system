package ua.yatsergray.backend.service.song.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.domain.dto.song.SongCategoryDTO;
import ua.yatsergray.backend.domain.entity.band.Band;
import ua.yatsergray.backend.domain.entity.song.SongCategory;
import ua.yatsergray.backend.domain.request.song.SongCategoryCreateRequest;
import ua.yatsergray.backend.domain.request.song.SongCategoryUpdateRequest;
import ua.yatsergray.backend.exception.ChildEntityExistsException;
import ua.yatsergray.backend.exception.band.NoSuchBandException;
import ua.yatsergray.backend.exception.song.NoSuchSongCategoryException;
import ua.yatsergray.backend.exception.song.SongCategoryAlreadyExistsException;
import ua.yatsergray.backend.mapper.song.SongCategoryMapper;
import ua.yatsergray.backend.repository.band.BandRepository;
import ua.yatsergray.backend.repository.song.SongCategoryRepository;
import ua.yatsergray.backend.repository.song.SongRepository;
import ua.yatsergray.backend.service.song.SongCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SongCategoryServiceImpl implements SongCategoryService {
    private final SongCategoryMapper songCategoryMapper;
    private final SongCategoryRepository songCategoryRepository;
    private final BandRepository bandRepository;
    private final SongRepository songRepository;

    @Autowired
    public SongCategoryServiceImpl(SongCategoryMapper songCategoryMapper, SongCategoryRepository songCategoryRepository, BandRepository bandRepository, SongRepository songRepository) {
        this.songCategoryMapper = songCategoryMapper;
        this.songCategoryRepository = songCategoryRepository;
        this.bandRepository = bandRepository;
        this.songRepository = songRepository;
    }

    @Override
    public SongCategoryDTO addSongCategory(SongCategoryCreateRequest songCategoryCreateRequest) throws NoSuchBandException, SongCategoryAlreadyExistsException {
        Band band = bandRepository.findById(songCategoryCreateRequest.getBandId())
                .orElseThrow(() -> new NoSuchBandException(String.format("Band with id=\"%s\" does not exist", songCategoryCreateRequest.getBandId())));

        if (songCategoryRepository.existsByBandIdAndName(songCategoryCreateRequest.getBandId(), songCategoryCreateRequest.getName())) {
            throw new SongCategoryAlreadyExistsException(String.format("Song category with bandId=\"%s\" and name=\"%s\" already exists", songCategoryCreateRequest.getBandId(), songCategoryCreateRequest.getName()));
        }

        SongCategory songCategory = SongCategory.builder()
                .name(songCategoryCreateRequest.getName())
                .band(band)
                .build();

        return songCategoryMapper.mapToSongCategoryDTO(songCategoryRepository.save(songCategory));
    }

    @Override
    public Optional<SongCategoryDTO> getSongCategoryById(UUID songCategoryId) {
        return songCategoryRepository.findById(songCategoryId).map(songCategoryMapper::mapToSongCategoryDTO);
    }

    @Override
    public List<SongCategoryDTO> getAllSongCategories() {
        return songCategoryMapper.mapAllToSongCategoryDTOList(songCategoryRepository.findAll());
    }

    @Override
    public SongCategoryDTO modifySongCategoryById(UUID songCategoryId, SongCategoryUpdateRequest songCategoryUpdateRequest) throws NoSuchSongCategoryException, SongCategoryAlreadyExistsException {
        SongCategory songCategory = songCategoryRepository.findById(songCategoryId)
                .orElseThrow(() -> new NoSuchSongCategoryException(String.format("Song category with id=\"%s\" does not exist", songCategoryId)));

        if (!songCategoryUpdateRequest.getName().equals(songCategory.getName()) && songCategoryRepository.existsByBandIdAndName(songCategory.getBand().getId(), songCategoryUpdateRequest.getName())) {
            throw new SongCategoryAlreadyExistsException(String.format("Song category with bandId=\"%s\" and name=\"%s\" already exists", songCategory.getBand().getId(), songCategoryUpdateRequest.getName()));
        }

        songCategory.setName(songCategoryUpdateRequest.getName());

        return songCategoryMapper.mapToSongCategoryDTO(songCategoryRepository.save(songCategory));
    }

    @Override
    public void removeSongCategoryById(UUID songCategoryId) throws NoSuchSongCategoryException, ChildEntityExistsException {
        if (!songCategoryRepository.existsById(songCategoryId)) {
            throw new NoSuchSongCategoryException(String.format("Song category with id=\"%s\" does not exist", songCategoryId));
        }

        checkIfSongCategoryHasChildEntity(songCategoryId);

        songCategoryRepository.deleteById(songCategoryId);
    }

    private void checkIfSongCategoryHasChildEntity(UUID songCategoryId) throws ChildEntityExistsException {
        long songCategoryChildEntityAmount = songRepository.countBySongCategoryId(songCategoryId);

        if (songCategoryChildEntityAmount > 0) {
            throw new ChildEntityExistsException(String.format("%s Song(s) depend(s) on the Song category with id=\"%s\"", songCategoryChildEntityAmount, songCategoryId));
        }
    }
}
